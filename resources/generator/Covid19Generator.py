#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
This file generates CSV files simulating transmission of COVID-19 for TSE's 2020 HPP Project
Created on Sun Mar 29 22:45:28 2020
@author: gmuller
"""

#import os
#os.chdir("/home/gmuller/2019_TSE/Cours/FISE2-HPP/hpp/resources/generator")


import numpy as np
import pandas as pd
import datetime, pytz
import random
import math

from Covid19Utils import Covid19Utils

# DONE: 1) génération des âges des patients en fonction de la pyramide des âges
# DONE: 2) more chances to be infected by a recent patient
# DONE: 3) plus contaminations dans pays ou plus de patients
# DONE: 4) de plus en plus d'infections en fonction du nombre d'infectés sur les X derniers jours ?
# TODO: 5) Find more fine-grained AGE_PYRAMID / F/LNames / REASONS

# TODO: Vérifier l'exponentialité de la séquence temporelle + la réalité de la séuqence d'id de transmission

# To enable debugging and repeatability
random.seed(42)

# Total number of patients to generate
OUT_DIR = "GeneratedFiles/"

## Time management
# Illness starts in China on 11/17/2019 (https://fr.wikipedia.org/wiki/Pand%C3%A9mie_de_Covid-19)
START_TIME = Covid19Utils.datetime2epoch(datetime.datetime(2019,11,17, 0,0,0,0, pytz.utc))
END_TIME   = Covid19Utils.datetime2epoch(datetime.datetime(2020, 5,12, 0,0,0,0, pytz.utc))

# Total number of patients to generate
#MAX_PATIENTS = 1_000
MAX_PATIENTS = 1_000_000

# Rate of patient for with the source of infection is 'unknown'
#UNKNOWN_RATE = .20
UNKNOWN_RATE = .20

# Names of the countries MUST ALWAYS BE OF SIZE 8!!!
#COUNTRIES = [ 'China', 'Italy', 'Spain', 'France', 'USA', 'Germany', 'Brazil', 'NKorea' ]
COUNTRIES = [ 'France', 'Italy', 'Spain']
NB_COUNTRIES = len(COUNTRIES)

# Lists of commons surnames  & given names
F_NAMES = pd.read_csv('./new-top-firstNames.csv')
L_NAMES = pd.read_csv('./new-top-surnames.csv')

# Arrays of composite elts to generate fake reasons for having been infected
REASONS_TYPE  = [ 'promenade', 'sport', 'course à pieds', 'vélo', 'basket', 'handball', 'escalade', 'kung-fu', 'apéro', 'courses', 'travail']
REASONS_WITH  = [ 'les enfants', 'le chien', 'le fils', 'mon fils', 'la fille', 'ma fille', 'mon père', 'ma mère', 'le père', 'la mère', 'le grand-père', 'la grand-mère', 'la copine', 'le copain', 'un copain', 'une copine', 'des copains', 'des copines', 'un collègue', 'des collègues']
REASONS_PLACE = [ 'en ville', 'à la campagne', 'au marché', 'en grande surface', 'au super maché', "à la salle de gym" ]

def which_country(patient_id, chains_of_infections):
    for key,value in chains_of_infections.items():
        if patient_id in value:
            return key

def select_infector(pid, country_inf_chain): # DONE2
    if (random.randint(0,99)/100.<UNKNOWN_RATE):             # for UNKNOWN_RATE patients, source is known
        return "unknown"
    else:
        pos = country_inf_chain.index(pid)
        if pos == 0:
            return "unknown"   # First patient in the country => contaminator unkn
        else:
            if pos < 20:
                rnd = random.randint(0, pos-1)       # if few patients => select within all predecessors
            else:
                rnd = random.randint(round(pos/2), pos-1)   # if many patients => select only in the last half/most recents
        return(str(country_inf_chain[rnd]))

def compute_age_pyramid():
    """Gets the actual data for age pyramid and computes a probas dict"""
    ages = pd.read_csv("age-pyramid-world-2019.csv")
    menpluswomen = np.sum(ages, axis=1)
    total = np.sum(menpluswomen)
    menpluswomen /= total
    return(dict(zip(range(1,len(menpluswomen)), menpluswomen)))
AGE_PYRAMID = compute_age_pyramid()

class Patient:
    """A patient representation"""
    def __init__(self, pid, country_inf_chain, diag_time):
        self.pers_id = pid
        self.pers_fname = F_NAMES.values[random.randint(0,len(F_NAMES)-1)][1]
        self.pers_lname = L_NAMES.values[random.randint(0,len(L_NAMES)-1)][1]
        age = random.randint(0,4)+5*Covid19Utils.biased_random(AGE_PYRAMID)  # DONE1
        self.pers_birth = datetime.datetime(2020-age,
                                            random.randint(1,12),
                                            random.randint(1,28));
        self.diag_ts = diag_time # converts in long (date since epoch)
        # more chances to be infected by recent patients (id is also the number of patients generated until now :) )
        self.cont_by = select_infector(pid, country_inf_chain);  # DONE2
        self.cont_reason = REASONS_TYPE[random.randint(0, len(REASONS_TYPE)-1)] + \
                           " avec " + REASONS_WITH[random.randint(0, len(REASONS_WITH)-1)] + " " + \
                           REASONS_PLACE[random.randint(0, len(REASONS_PLACE)-1)]
    def __str__(self):
        return "[id"+str(self.pers_id)+": "+self.pers_fname+" "+self.pers_lname+"/"+str(2020-self.pers_birth.year)+"yo]" + \
               " contaminated by id"+str(self.cont_by)+" on "+str(self.diag_ts)+" because "+ self.cont_reason
    def __csv__(self):
        return str(self.pers_id)+", \""+self.pers_fname+"\", \""+self.pers_lname+"\", "+str(self.pers_birth)+", " + \
               str(self.diag_ts)+", "+str(self.cont_by)+", \""+self.cont_reason+"\"\n"
#p = Patient(10, datetime.datetime.now())
#print(p.__str__()+"\n") ; print(p.__csv__())


DEBUG = False
#DEBUG = True
if __name__ == "__main__":
    # Prepares chains of infections by country
    all_patients_ids = [x for x in range(0,MAX_PATIENTS)]
    random.shuffle(all_patients_ids)
    chains_of_infections = {}
    for country_counter in range(0, NB_COUNTRIES):
        curr_country = COUNTRIES[country_counter]
        split_size   = len(all_patients_ids)/NB_COUNTRIES
        start_split  = math.floor(country_counter*split_size)
        end_split    = math.floor((country_counter+1)*split_size)
        if DEBUG: print("["+str(start_split)+","+str(end_split)+"]")
        chains_of_infections[curr_country] = [all_patients_ids[x] for x in range(start_split,end_split)]
        if DEBUG: print(chains_of_infections[curr_country])
        if DEBUG: print(len(chains_of_infections[curr_country]))
        list.sort(chains_of_infections[curr_country])

    country_files = {}
    for c in COUNTRIES:
        country_files[c] = open(OUT_DIR+"/"+c+".csv", "w",encoding="utf8")

    # Generate patients
    for patient_id in range(0, MAX_PATIENTS):
        # Find patient's country
        country = which_country(patient_id, chains_of_infections)

        diag_time = (END_TIME-START_TIME)*math.log(patient_id+1)/math.log(MAX_PATIENTS) + START_TIME
        if DEBUG: print(">> Infection date:" + str(Covid19Utils.epoch2datetime(diag_time)))

        # Generate a patient
        patient = Patient(patient_id, chains_of_infections[country], diag_time)

        if DEBUG: print("> Adding new patient " + str(patient) + " into " + country + " file...")
        # actually write the patient data in the correct CSV file
        country_files[country].write(patient.__csv__());

    # Close files
    for c in COUNTRIES:
        country_files[c].close()


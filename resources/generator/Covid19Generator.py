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

# Total number of patients to generate
#MAX_PATIENTS = 1_000
MAX_PATIENTS = 2_000_000

# Stages of infection.
## Influences:
## - the % of most recent patents susceptible to be source of infection
## - the probability to put the new patient in a given country
#INFECTION_STAGES = [ 100, 1_000, 5_000, 10_000, 25_000 ]
INFECTION_STAGES = [ 100, 1_000, 5_000, 10_000, 25_000 ]

# Rate of patient for with the source of infection is 'unknown'
#UNKNOWN_RATE = .20
UNKNOWN_RATE = .20

# Names of the countries MUST ALWAYS BE OF SIZE 8!!!
#COUNTRIES = [ 'China', 'Italy', 'Spain', 'France', 'USA', 'Germany', 'Brazil', 'NKorea' ]
COUNTRIES = [ 'Italy', 'Spain', 'France', 'China', 'USA', 'Germany', 'Brazil', 'NKorea' ]
# Stores nb infections in each country
infections_by_country = { k: 0 for k in COUNTRIES }
# Stores prob of infections in each country
probas_by_country = { k: 0 for k in COUNTRIES }

# Lists of commons surnames  & given names
F_NAMES = pd.read_csv('./new-top-firstNames.csv')
L_NAMES = pd.read_csv('./new-top-surnames.csv')

# Arrays of composite elts to generate fake reasons for having been infected
REASONS_TYPE  = [ 'promenade', 'sport', 'course à pieds', 'vélo', 'basket', 'handball', 'escalade', 'kung-fu', 'apéro', 'courses', 'travail']
REASONS_WITH  = [ 'les enfants', 'le chien', 'le fils', 'mon fils', 'la fille', 'ma fille', 'mon père', 'ma mère', 'le père', 'la mère', 'le grand-père', 'la grand-mère', 'la copine', 'le copain', 'un copain', 'une copine', 'des copains', 'des copines', 'un collègue', 'des collègues']
REASONS_PLACE = [ 'en ville', 'à la campagne', 'au marché', 'en grande surface', 'au super maché', "à la salle de gym" ]

def select_infectors(pid): # DONE2
    """Defines which % of the least recently infected patients are the source of the infection for new patients"""
    if pid<INFECTION_STAGES[0]:
        infectors = 0  # all patients are infectious
    elif pid<INFECTION_STAGES[1]:
        infectors = round(.25*pid)   # last 75% are infectious  ## 75<.<750  # TODO: BEWARE, THERE ARE DROPDOWNS IN THE NB OF INFECTORS DURING THE STEPPING :{
    elif pid<INFECTION_STAGES[2]:
        infectors = round(.5*pid)    # 50 last % are            ## 500<.<2500
    elif pid<INFECTION_STAGES[3]:
        infectors = round(.75*pid)   # 25 last %                ## 1250<.<7500
    else:
        infectors = round(.90*pid)   # 10 last %                ## >1000
    return((infectors,pid))

def compute_country_probas(pid, infections_by_country): # DONE3
    """At the begining: try to simulate actual countries
    Then, the more a country already has patients, the more a new patient is supposed to be added in its stats."""
    probas = { k: 0 for k in COUNTRIES }
    if pid<INFECTION_STAGES[0]:
        probas[COUNTRIES[0]] = 1.
    elif pid<INFECTION_STAGES[1]:
        probas[COUNTRIES[0]] = .75
        probas[COUNTRIES[1]] = .25
    elif pid<INFECTION_STAGES[2]:
        probas[COUNTRIES[0]] = .50
        probas[COUNTRIES[1]] = .25
        probas[COUNTRIES[2]] = .25
    elif pid<INFECTION_STAGES[3]:  # TODO: ensure sum(probas)=1.
        probas[COUNTRIES[0]] = .25
        probas[COUNTRIES[1]] = .30
        probas[COUNTRIES[2]] = .30
        probas[COUNTRIES[3]] = .15
    elif pid<INFECTION_STAGES[4]:
        probas[COUNTRIES[0]]  = .05
        probas[COUNTRIES[1]]  = .325
        probas[COUNTRIES[2]]  = .325
        probas[COUNTRIES[3]]  = .20
        probas[COUNTRIES[4]]  = .03
        probas[COUNTRIES[5]]  = .03
        probas[COUNTRIES[6]]  = .03
        probas[COUNTRIES[7]]  = .01
    else: # for very large files (more the INFECTION_STAGES[4] patients)
        probas[COUNTRIES[0]]  = .01
        probas[COUNTRIES[1]]  = .30
        probas[COUNTRIES[2]]  = .30
        probas[COUNTRIES[3]]  = .25
        probas[COUNTRIES[4]]  = .07
        probas[COUNTRIES[5]]  = .03
        probas[COUNTRIES[6]]  = .03
        probas[COUNTRIES[7]]  = .01
### OLD TOTALLY STUPID CODE: I generated new proba based on what I have generated previously (=== OLD PROBAs!!!!)
#    total_infections = make_changes
#    probas = { k: v/total_infections for k, v in sorted(infections_by_country.items(), key=lambda item: item[1]) }
    return(probas)

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
    def __init__(self, pid, diag_time):
        self.pers_id = pid
        self.pers_fname = F_NAMES.values[random.randint(0,len(F_NAMES)-1)][1]
        self.pers_lname = L_NAMES.values[random.randint(0,len(L_NAMES)-1)][1]
        age = random.randint(0,4)+5*Covid19Utils.biased_random(AGE_PYRAMID)  # DONE1
        self.pers_birth = datetime.datetime(2020-age,
                                            random.randint(1,12),
                                            random.randint(1,28));
        self.diag_ts = Covid19Utils.datetime2epoch(diag_time) # converts in long (date since epoch)
        # more chances to be infected by recent patients (id is also the number of patients generated until now :) )
        first, last = select_infectors(pid);  # DONE2
        if (random.randint(0,99)/100.<UNKNOWN_RATE):         # for UNKNOWN_RATE patients, source is known
            self.cont_by = 'unknown'
        else:
            self.cont_by = str(random.randint(first, last))  # for the others, it's within the last infected, depending on INFECTION_STAGE
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


## Time management
# Illness starts in China on 11/17/2019 (https://fr.wikipedia.org/wiki/Pand%C3%A9mie_de_Covid-19)
timesequence = datetime.datetime(2020,1,1, 0,0,0,0, pytz.utc)
# Simulates exponential growth: The more patients there are, the more frequent are the infections (<=>lower is the time_offset between 2 subsequent patients)
time_offset = 24*3600 # initial time-to-next-infection (pid0->pid1) : 24hrs
# When to make changes: decrement the time_offset & recompute countries infection probas
make_changes = 100

DEBUG = False
#DEBUG = True
if __name__ == "__main__":
    # Creates country files
    country_files = {}
    for c in COUNTRIES:
        country_files[c] = open(OUT_DIR+"/"+c+".csv", "w")

    # Generate patients
    for patient_id in range(0, MAX_PATIENTS):
        # Initialize country probas
        probas_by_country = compute_country_probas(patient_id, infections_by_country)
        if DEBUG: print("> PROBAS_BY_COUNTRY="+str(probas_by_country))

        # Generate a patient
        patient = Patient(patient_id, timesequence)

        # Select a country
        country = Covid19Utils.biased_random(probas_by_country)  # DONE3
        if DEBUG: print("> Adding new patient " + str(patient) + " into " + country + " file...")
        # actually write the patient data in the correct CSV file
        country_files[country].write(patient.__csv__());
        infections_by_country[country] += 1

        # Prepare next iteration
        timesequence = Covid19Utils.epoch2datetime(Covid19Utils.datetime2epoch(timesequence)+(time_offset))
        if (patient_id % make_changes == 0):
            if DEBUG: print(">> Changing config...")
            probas_by_country = compute_country_probas(patient_id, infections_by_country) # DONE3
            infections_by_country = { k:0 for k,v in infections_by_country.items() }
            if DEBUG: print(">>> probas: "+str(probas_by_country))
            time_offset *= 1-(1/((patient_id+1)*MAX_PATIENTS)) ## TODO simpler equation ?

    # Close files
    for c in COUNTRIES:
        country_files[c].close()

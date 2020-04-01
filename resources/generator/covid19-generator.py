#!/usr/bin/env python3

import pandas as pd
import datetime, timezone
import random

#import os
#os.chdir("/home/gmuller/2019_TSE/Cours/FISE2-HPP/hpp/resources/generator")

random.seed(42)

MAX_PATIENTS = 10 #000

# Almost in actual order of countries where it appeared, counts nb patients already generated in each country
COUNTRIES = { 'China':0, 'Italy':0, 'Spain':0, 'France':0, 'USA':0, 'Germany':0, 'Brazil':0 }

F_NAMES = pd.read_csv('./new-top-firstNames.csv')
L_NAMES = pd.read_csv('./new-top-surnames.csv')

REASONS_TYPE  = [ 'promenade', 'sport', 'course à pieds', 'vélo', 'basket', 'handball', 'escalade', 'kung-fu', 'apéro', 'courses', 'travail']
REASONS_WITH  = [ 'les enfants', 'le chien', 'le fils', 'mon fils', 'la fille', 'ma fille', 'mon père', 'ma mère', 'le père', 'la mère', 'le grand-père', 'la grand-mère', 'la copine', 'le copain', 'un copain', 'une copine', 'des copains', 'des copines', 'un collègue', 'des collègues']
REASONS_PLACE = [ 'en ville', 'à la campagne', 'au marché', 'en grande surface', 'au super maché', "à la salle de gym" ]

class Patient:
    """A patient representation"""
    def __init__(self, id, diag_time):
        self.pers_id = id
        self.pers_name = F_NAMES.values[len(F_NAMES)][1]+" "+L_NAMES.values[len(L_NAMES)][1]
        self.pers_age = random.randint(6,100); # TODO ? mettre des poids en fonction de répartition réelle population ? :)
        self.diag_ts = diag_time       # TODO convert in long
        self.cont_by = random.randint(id)      # TODO ? mettre plus de poids aux + anciens patients
        self.cont_reason = REASONS_TYPE[random.randint(0,len(REASONS_TYPE)-1)] + " avec " + REASONS_WITH[random.randint(0,len(REASONS_WITH)-1)] + REASONS_PLACE[random.randint(0,len(REASONS_PLACE)-1)]
    def __str__(self):
        return "["+self.pers_id+": "+self.pers_name+"/"+self.pers_age+"]" + "contaminated by "+self.cont_by+" on "+self.diag_ts+" because "+ self.cont_reason

## Time management
# Illness starts in China on 11/17/2019 (https://fr.wikipedia.org/wiki/Pand%C3%A9mie_de_Covid-19)
timesequence = datetime.datetime(2020,1,1, 0,0,0,0, timezone.utc)  ## TODO: is UTC really the default?
# The more patients there are => the more frequent is the infection (lower is the time_offset)
# Simulates exponential growth
time_offset = datetime.time(24,0,0) # 2nd infection occurs 24h atfer 1rst?
# Every "time_offset_change" (#new patients), we decrease the time_offset
time_offset_change = 10 # not too high so that beginning of infection is testable by students early!

for patient_id in range(0, MAX_PATIENTS):
    patient = Patient(patient_id, timesequence)
    timesequence += time_offset
    if (patient_id % time_offset_change == 0):
        ### ICI
        time_offset -= datetime.datetime.fromtimestamp(patient_id/1000) # TODO how to divide time??? https://docs.python.org/2/library/datetime.html#time-objects
    
    country = list(COUNTRIES.keys())[random.randint(0,len(COUNTRIES))] # TODO the more a country already has patients, the more changes the new patients goes in its stats.
    print(">> Adding new patient " + patient + " for " + country + "...")
    # TODO: actually write the data in the correct CSV file


#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
This file implements basic methods for TSE's HPP Project 2020
Created on Sun Apr  5 16:38:40 2020
@author: gmuller
"""

import random
import time, datetime
#import numpy as np

class Covid19Utils:

    @staticmethod
    def biased_random(proba_dict):
        # TODO: assert sum proba = 1.0
        rd = random.uniform(0., 1.)
        sm = 0
        for k,v in proba_dict.items():
            sm += v
            if rd <= sm: return(k)
        return(list(proba_dict.keys())[len(proba_dict)-1])    # TODO: assert("Error: sum of probas is not 1.0") DOES NOT WORK???
        
    @staticmethod
    def epoch2datetime(epoch):
        return(datetime.datetime.fromtimestamp(epoch))

    @staticmethod
    def datetime2epoch(dt):        
        return(time.mktime(dt.timetuple()))
            

if __name__ == "__main__":
    dt = datetime.datetime.now()
    epo = Covid19Utils.datetime2epoch(dt=dt)
    dt2 = Covid19Utils.epoch2datetime(epo)
    
    NB_VAL = 1000
    pdict = { "France": .5, "Italy": .5 }
    out = { k:0 for k in pdict.keys() };
    for i in range(0, NB_VAL):
        val = Covid19Utils.biased_random(pdict);
        out[val] +=1
    print({ k: v/NB_VAL for k,v in out.items() })
    
    
def test_pid(pid):
    if pid<100:
        print(1.)
    elif pid<1_000:
        print(.75)
    elif pid<5_000:
        print(.5)
    else:
        print(.2)

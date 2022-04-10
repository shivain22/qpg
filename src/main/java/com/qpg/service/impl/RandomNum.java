package com.qpg.service.impl;

import java.lang.String;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class RandomNum {

    public static final void main(String[] args){
        final Random r = new Random();
        final Set<Integer> s = new HashSet<>();
        for(int i = 0; i < 6; i++){
                while(true) {
                int num = r.nextInt(6) + 1;
                if (s.contains(num) == false) {
                    s.add(num);
                    System.out.println(num);
                    break;
                }
            }
        }
    }
}

package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    public void testCharacter()
    {
        Character c = new Character("john", 5,3,2);

        c.initiateSkills(new int[]{0,0,0,2,0,0,0,0,1,3,0,0,0});

        System.out.println("Initiated skills.");
    }

}
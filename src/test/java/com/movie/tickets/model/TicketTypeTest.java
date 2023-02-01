package com.movie.tickets.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TicketTypeTest {

    @Test
    public void shouldGetChildrenTypeGivenLessThan11(){
        assertEquals(TicketType.Children, TicketType.getValueOf(6));
    }
    @Test
    public void shouldGetTeenTypeGivenAgeEquals11(){
        assertEquals(TicketType.Teen, TicketType.getValueOf(11));
    }
    @Test
    public void shouldGetTeenTypeGivenLessThan18(){
        assertEquals(TicketType.Teen, TicketType.getValueOf(15));
    }
    @Test
    public void shouldGetAdultTypeGivenAgeEquals18(){
        assertEquals(TicketType.Adult, TicketType.getValueOf(18));
    }
    @Test
    public void shouldGetAdultTypeGivenLessThan65(){
        assertEquals(TicketType.Adult, TicketType.getValueOf(45));
    }
    @Test
    public void shouldGetSeniorTypeGivenAgeEquals65(){
        assertEquals(TicketType.Senior, TicketType.getValueOf(65));
    }
    @Test
    public void shouldGetSeniorTypeGivenAgeBiggerThan65(){
        assertEquals(TicketType.Senior, TicketType.getValueOf(70));
    }

}
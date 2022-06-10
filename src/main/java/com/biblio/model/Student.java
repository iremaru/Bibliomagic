package com.biblio.model;

public class Student {

    private int code;
    private String name;
    private String surname;

    private House house;

    private String year;

    public enum House {
        Slytherin("slytherin"),
        Ravenclaw("ravenclaw"),
        Hufflepuff("hufflepuff"),
        Gryffindor("gryffindor"),
        NoHouse("Notthousely")
        ;

        private String label;

        private House(String label)
        {
            this.label=label;
        }

        public String getLabel() {return label;}
        public static House getHouse(String house)
        {
            if(house.equals(Slytherin.label))
            {
                return Slytherin;
            } else if (house.equals(Ravenclaw.label))
            {
                return Ravenclaw;
            }else if (house.equals(Hufflepuff.label))
            {
                return Hufflepuff;
            }else if (house.equals(Gryffindor.label))
            {
                return Gryffindor;
            } else {
                return NoHouse;
            }
        }
    }

    public Student(int code, String name, String surname, House house, String year) {
        this.code = code;
        this.name = name;
        this.surname = surname;
        this.house = house;
        this.year = year;
    }
    public Student(String name, String surname, House house, String year) {
        this.name = name;
        this.surname = surname;
        this.house = house;
        this.year = year;
    }

    public House getHouse() {
        return house;
    }
    public String getHouseString(){return house.label;}

    public void setHouse(House house) {
        this.house = house;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}

package com.example.salonprojekt;

public class Edit {
        private String name;
        private int standardDuration;
        private double standardPrice;

        public Edit(String name, int standardDuration, double standardPrice) {
            this.name = name;
            this.standardDuration = standardDuration;
            this.standardPrice = standardPrice;
        }

        public String getName() {
            return name;
        }

        public int getStandardDuration() {
            return standardDuration;
        }

        public double getStandardPrice() {
            return standardPrice;
        }
    }


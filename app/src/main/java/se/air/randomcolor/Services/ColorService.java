package se.air.randomcolor.Services;

import java.util.Random;

import se.air.randomcolor.Models.Color;

public class ColorService {

    public int[] hexToRqb(String colorHEX){
        if(!checkHEX(colorHEX)){
            throw new RuntimeException("Error: Wrong HEX code.");
        }

        if(colorHEX.length() == 7) {
            colorHEX = deleteFirstLetter(colorHEX);
        }

        int[] rgb = new int[3];

        int bigint = Integer.parseInt(colorHEX, 16);
        rgb[0] = (bigint >> 16) & 255;
        rgb[1] = (bigint >> 8) & 255;
        rgb[2] = bigint & 255;

        return rgb;
    }

    private boolean checkHEX(String hex){
        if (hex.length() == 7 && hex.charAt(0) == '#') {
            hex = deleteFirstLetter(hex);
        } else if (hex.length() != 6) {
            return false;
        }

        boolean underFlag;
        boolean flag = true;
        String hexLetters = "0123456789abcdef";
        for (int i = 0; i < hex.length() && flag; i++) {
            underFlag = false;
            for(int j = 0; j < hexLetters.length() && !underFlag; j++) {
                if (hex.charAt(i) == hexLetters.charAt(j)){
                    underFlag = true;
                }
            }
            flag = underFlag;
        }
        return flag;
    }

    private String deleteFirstLetter(String colorHEX) {
        return colorHEX.substring(1);
    }

    public Color getRandomColor() {  // возвращает HEX код
        Random random = new Random();
        String letters = "0123456789abcdef";
        StringBuilder color = new StringBuilder();
        color.append('#');
        for(int i = 0; i < 6; i++){
            color.append(letters.charAt(random.nextInt(letters.length())));
        }
        return new Color(color.toString());
    }
}

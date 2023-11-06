package org.rev.utils;

import static org.rev.utils.AppScanner.getScanner;
import static org.rev.utils.Utils.print;

public class AppInput {
    public static int enterInt(String msg) throws AppException{
        print(msg);
        int input = 0;
        try {
            input = Integer.parseInt(getScanner().nextLine());
        } catch (Exception e) {
            throw new AppException(StringUtils.INVALID_CHOICE);
        }
        return input;
    }

    public static double enterDouble(String msg) throws AppException{
        print(msg);
        double input = 0;
        try {
            input = Double.parseDouble(getScanner().nextLine());
        } catch (Exception e) {
            throw new AppException(StringUtils.INVALID_CHOICE);
        }
        return input;
    }

    public static String enterString(String msg){
        print(msg);
        return getScanner().nextLine();
    }

}

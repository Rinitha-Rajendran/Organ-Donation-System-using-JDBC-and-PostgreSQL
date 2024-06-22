
import orgav.*;
import donp.donor;
import fam1.donar_family1;
import log.login;
import orgav.OrganAvailabilityCheck;
import orgreg.OrganRegistration;
import recp.recipient;


import java.util.Scanner;

import static orgav.OrganAvailabilityCheck.organAvailable;


public class Main {
    public static void main(String args[]) {
        String chlr;
        String chp;
        System.out.println("press r for registration press l for login");
        Scanner scanner = new Scanner(System.in);
        chlr = scanner.nextLine();
        while (!(chlr.equals("l") || chlr.equals("r"))) {
            System.out.println("please enter vaild choice");
            chlr = scanner.nextLine();
        }
        if (chlr.equals("r")) {
            System.out.println("press d to register as donor or press r to register as recipcipent");
            chp = scanner.nextLine();
            while (!(chp.equals("d") || chp.equals("r"))) {
                System.out.println("please enter vaild choice");
                chp = scanner.nextLine();
            }
            if (chp.equals("d")) {
                donor.main(args);

                boolean af = donor.getAf();
                if (af) {
                    login.main(args);
                    boolean isAuthenticated = login.isAuth;
                    if(!isAuthenticated){
                        System.out.println("Try to login again");
                    }
                    else{
                        System.out.println("family permission");
                        donar_family1.main(args);
                        OrganRegistration.main(args);

                    }

                }

            }
            else {
                if (chp.equals("r")){
                    recipient.main(args);
                    boolean afr=recipient.getAfr();
                    if(afr){
                        login.main(args);
                        boolean isAuthenticated = login.isAuth;
                        if(!isAuthenticated){
                            System.out.println("Try to login again");
                        }
                        else{
                            OrganAvailabilityCheck.main(args);
                            if(organAvailable){


                            }
                            else{
                                System.out.println("organ is not available");

                            }

                        }


                    }

                }

            }
        }
        if(chlr.equals("l")){
            System.out.println("press d to login as donor or press r to login as recipcipent");
            chp=scanner.nextLine();
            if (chp.equals("d")) {
                login.main(args);
                boolean isAuthenticated = login.isAuth;
                if(!isAuthenticated){
                        System.out.println("Try to login again");
                }
                else{
                    System.out.println("family permission");
                    donar_family1.main(args);
                    OrganRegistration.main(args);

                    }

                }
            else{
                login.main(args);
                boolean isAuthenticated = login.isAuth;
                if(!isAuthenticated){
                    System.out.println("Try to login again");
                }
                else{
                    OrganAvailabilityCheck.main(args);
                    if(organAvailable){



                    }
                    else{
                        System.out.println("organ is not available");

                    }

                }
            }

            }


        }

    }

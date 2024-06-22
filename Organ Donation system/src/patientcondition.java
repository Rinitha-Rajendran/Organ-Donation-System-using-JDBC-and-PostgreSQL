import java.util.*;
import java.util.concurrent.*;

class MedicalReportNotFoundException extends Exception {
    public MedicalReportNotFoundException(String message) {
        super(message);
    }
}

class MedicalReport {
    private String report;

    public MedicalReport(String report) {
        this.report = report;
    }

    public String getReport() {
        return report;
    }
}

abstract class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getMedicalInfo();
}

class Patient extends Person {
    private String condition;
    private double bpLevel;
    private double sugarLevel;
    private double creatinineLevel;
    private int breathingRate;

    public Patient(String name, String condition, double bpLevel, double sugarLevel, double creatinineLevel, int breathingRate) {
        super(name);
        this.condition = condition;
        this.bpLevel = bpLevel;
        this.sugarLevel = sugarLevel;
        this.creatinineLevel = creatinineLevel;
        this.breathingRate = breathingRate;
    }

    public String getCondition() {
        return condition;
    }

    public double getBpLevel() {
        return bpLevel;
    }

    public double getSugarLevel() {
        return sugarLevel;
    }

    public double getCreatinineLevel() {
        return creatinineLevel;
    }

    public int getBreathingRate() {
        return breathingRate;
    }

    @Override
    public String getMedicalInfo() {
        return "Patient: " + getName() + ", Condition: " + getCondition();
    }
}

class Doctor extends Person {
    private String specialization;

    public Doctor(String name, String specialization) {
        super(name);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String getMedicalInfo() {
        return "Doctor: " + getName() + ", Specialization: " + getSpecialization();
    }

    public MedicalReport checkPatientConditionAndProvideReport(Patient patient, String report) {
        String message = "Doctor " + getName() + " checked " + patient.getName() + "'s condition after surgery. ";

        if (patient.getCondition().equalsIgnoreCase("PostSurgery")) {
            double bpLevel = patient.getBpLevel();
            double sugarLevel = patient.getSugarLevel();
            double creatinineLevel = patient.getCreatinineLevel();
            int breathingRate = patient.getBreathingRate();

            if (isWithinNormalRange(bpLevel, 90, 120) && isWithinNormalRange(sugarLevel, 70, 100)
                    && isWithinNormalRange(creatinineLevel, 0.5, 1.2) && isWithinNormalRange(breathingRate, 12, 18)) {
                System.out.println("good");
                message += "Patient is responding well to treatment.";
            } else {
                message += "Patient should be under observation. ";
                if (!isWithinNormalRange(bpLevel, 90, 120)) {
                    message += "BP levels are not within the normal range. ";
                }
                if (!isWithinNormalRange(sugarLevel, 70, 100)) {
                    message += "Sugar levels are not within the normal range. ";
                }
                if (!isWithinNormalRange(creatinineLevel, 0.5, 1.2)) {
                    message += "Creatinine levels are not within the normal range. ";
                }
                if (!isWithinNormalRange(breathingRate, 12, 18)) {
                    message += "Breathing rate is not within the normal range. ";
                }
            }
        } else {
            message += "No specific condition found.";
        }

        System.out.println(message);
        return new MedicalReport(report);
    }

    private boolean isWithinNormalRange(double value, double lowerBound, double upperBound) {
        // Checking for unrealistic extreme values
        if (value <= 0 || value > 1000) {
            return false; // Values such as 0 or greater than 1000 might be unrealistic
        }
        return value >= lowerBound && value <= upperBound;
    }
}

class OrganDonationSystem {
    private List<Person> persons;
    private Map<String, MedicalReport> medicalReports;
    private ExecutorService executor;

    public OrganDonationSystem() {
        persons = new ArrayList<>();
        medicalReports = new HashMap<>();
        executor = Executors.newFixedThreadPool(5); // Adjust the thread pool size as needed
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void generateMedicalReport(Person person, String report) {
        if (person instanceof Patient) {
            Runnable task = () -> {
                MedicalReport medicalReport = new MedicalReport(report);
                medicalReports.put(person.getName(), medicalReport);
            };
            executor.execute(task);
        }
    }

    public MedicalReport getMedicalReport(String personName) throws MedicalReportNotFoundException {
        if (!medicalReports.containsKey(personName)) {
            throw new MedicalReportNotFoundException("Medical report not found for " + personName);
        }
        return medicalReports.get(personName);
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void shutdown() {
        executor.shutdown();
    }
}

public class Hain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrganDonationSystem organSystem = new OrganDonationSystem();

        System.out.println("Welcome to the Organ Donation System!");

        createPersons(scanner, organSystem);

        generatePatientReports(organSystem);

        checkPatientReports(scanner, organSystem);

        organSystem.shutdown();
        scanner.close();
    }

    private static void createPersons(Scanner scanner, OrganDonationSystem organSystem) {
        System.out.println("Enter details for Patient");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Condition: ");
        String condition = scanner.next();

        double bpLevel = getDoubleInput(scanner, "BP Level: ");
        double sugarLevel = getDoubleInput(scanner, "Sugar Level: ");
        double creatinineLevel = getDoubleInput(scanner, "Creatinine Level: ");

        int breathingRate = getIntInput(scanner, "Breathing Rate: ");

        organSystem.addPerson(new Patient(name, condition, bpLevel, sugarLevel, creatinineLevel, breathingRate));

        System.out.println("Enter details for Doctor");
        System.out.print("Name: ");
        name = scanner.next();
        System.out.print("Specialization: ");
        String specialization = scanner.next();
        organSystem.addPerson(new Doctor(name, specialization));
    }

    private static double getDoubleInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid decimal number.");
            scanner.next(); // Consume the invalid input
            System.out.print(prompt);
        }
        return scanner.nextDouble();
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.next(); // Consume the invalid input
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    private static void generatePatientReports(OrganDonationSystem organSystem) {
        List<Person> persons = organSystem.getPersons();
        ExecutorService executor = Executors.newFixedThreadPool(persons.size());
        List<Future<?>> futures = new ArrayList<>();

        for (Person person : persons) {
            if (person instanceof Patient) {
                futures.add(executor.submit(() -> {
                    Patient patient = (Patient) person;
                    String report;
                    if (patient.getCondition().equalsIgnoreCase("PostSurgery")) {
                        report = "Patient " + patient.getName() + " is recovering well after surgery.";
                    } else {
                        report = "Patient " + patient.getName() + " is in stable condition.";
                    }
                    organSystem.generateMedicalReport(person, report);
                }));
            }
        }

        executor.shutdown();
        try {
            for (Future<?> future : futures) {
                future.get(); // Wait for each task to complete
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error in generating reports: " + e.getMessage());
        }

        // Once all reports are generated, proceed to check them...
        checkPatientReports(new Scanner(System.in), organSystem);
    }

    private static void checkPatientReports(Scanner scanner, OrganDonationSystem organSystem) {
        System.out.println("\nChecking Reports:");
        for (Person person : organSystem.getPersons()) {
            if (person instanceof Patient || person instanceof Doctor) {
                System.out.println("Checking report for " + person.getName());
                try {
                    MedicalReport report = organSystem.getMedicalReport(person.getName());
                    System.out.println("Medical Report: " + report.getReport());
                } catch (MedicalReportNotFoundException e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        }
    }
}
package thesesstats;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    private static final String BACHELOR = "Bachelor";

    private static final String FIRST = "Erstgutachten";

    private static final double[] GRADES = new double[] {1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 5.0};

    private static final String MASTER = "Master";

    private static final String PA = "Praxisarbeiten";

    private static final String RESULT = "result.txt";

    private static final String SECOND = "Zweitgutachten";

    private static final String STATISTICS = "Statistik";

    private static final String STATISTICS_FILE = "statistics%s%s%d.tex";

    public static void main(final String[] args) throws IOException {
        if (args.length < 1 || args.length == 1 && "-h".equals(args[0])) {
            System.out.println(
                "Aufruf mit ROOT YEAR [MODE], wobei MODE = POINTS | REVIEWER, TYPE mit REVIEWER = ALL | FIRST | SECOND "
                + "und TYPE = ALL | ALL_BUT_PA | BA | MA | PA"
            );
            return;
        }
        final File root = new File(args[0]);
        final int year = Integer.parseInt(args[1]);
        if (args.length == 3 && "points".equals(args[2].toLowerCase())) {
            Main.writePoints(Main.countPoints(root, year), root.toPath().resolve("points" + year + ".txt").toFile());
            return;
        }
        if (args.length == 2) {
            for (final Reviewer reviewer : Reviewer.values()) {
                for (final ThesisType type : ThesisType.values()) {
                    Main.statistics(root, reviewer, type, year);
                }
            }
            return;
        }
        final Reviewer reviewer = args.length >= 3 ? Reviewer.valueOf(args[2]) : Reviewer.FIRST;
        final ThesisType type = args.length >= 4 ? ThesisType.valueOf(args[3]) : ThesisType.ALL;
        Main.statistics(root, reviewer, type, year);
    }

    private static int[] countGrades(
        final File root,
        final int year,
        final Reviewer reviewer,
        final ThesisType type
    ) throws IOException {
        final List<File> files;
        switch (reviewer) {
        case ALL:
            files = Main.findResultFiles(root.toPath().resolve(Main.FIRST), type, year);
            files.addAll(Main.findResultFiles(root.toPath().resolve(Main.SECOND), type, year));
            break;
        case FIRST:
            files = Main.findResultFiles(root.toPath().resolve(Main.FIRST), type, year);
            break;
        case SECOND:
            files = Main.findResultFiles(root.toPath().resolve(Main.SECOND), type, year);
            break;
        default:
            throw new IllegalStateException("Unknown Selection occurred!");
        }
        return Main.countGrades(files);
    }

    private static int[] countGrades(final List<File> files) throws IOException {
        final int[] result = new int[11];
        for (final File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine();
                final String grade = reader.readLine();
                switch (grade) {
                case "1,0":
                    result[0]++;
                    break;
                case "1,3":
                    result[1]++;
                    break;
                case "1,7":
                    result[2]++;
                    break;
                case "2,0":
                    result[3]++;
                    break;
                case "2,3":
                    result[4]++;
                    break;
                case "2,7":
                    result[5]++;
                    break;
                case "3,0":
                    result[6]++;
                    break;
                case "3,3":
                    result[7]++;
                    break;
                case "3,7":
                    result[8]++;
                    break;
                case "4,0":
                    result[9]++;
                    break;
                case "5,0":
                    result[10]++;
                    break;
                default:
                    throw new IOException("Could not parse grade " + grade + "!");
                }
            }
        }
        return result;
    }

    private static Points countPoints(final File root, final int year) throws IOException {
        final Points points = new Points();
        final Path theses = root.toPath().resolve("Abschlussarbeiten");
        final Path first = theses.resolve(Main.FIRST);
        final Path second = theses.resolve(Main.SECOND);
        points.bachelorFirst = Main.toTheses(Main.findResultFiles(first.resolve(Main.BACHELOR), year));
        final List<File> bachelorSecond = Main.findResultFiles(second.resolve(Main.BACHELOR), year);
        points.bachelorSecondLong = Main.toTheses(bachelorSecond.stream().filter(Main::isLong));
        points.bachelorSecondShort = Main.toTheses(bachelorSecond.stream().filter(Main::isNotLong));
        points.masterFirst = Main.toTheses(Main.findResultFiles(first.resolve(Main.MASTER), year));
        final List<File> masterSecond = Main.findResultFiles(second.resolve(Main.MASTER), year);
        points.masterSecondLong = Main.toTheses(masterSecond.stream().filter(Main::isLong));
        points.masterSecondShort = Main.toTheses(masterSecond.stream().filter(Main::isNotLong));
        points.practicalThesesFirst = Main.toTheses(Main.findResultFiles(first.resolve(Main.PA), year));
        final List<File> paSecond = Main.findResultFiles(second.resolve(Main.PA), year);
        points.practicalThesesSecondLong = Main.toTheses(paSecond.stream().filter(Main::isLong));
        points.practicalThesesSecondShort = Main.toTheses(paSecond.stream().filter(Main::isNotLong));
        points.practicalCheck =
            Files
            .list(root.toPath().resolve("Vorlesungen").resolve("Praxischeck").resolve("classes"))
            .filter(path -> path.getFileName().toString().startsWith(String.valueOf(year - 2000)))
            .map(
                path -> {
                    Path file;
                    try {
                        file =
                            Files
                            .list(path)
                            .filter(f -> f.getFileName().toString().endsWith(".txt"))
                            .findFirst()
                            .get();
                    } catch (final IOException e) {
                        throw new IllegalStateException(e);
                    }
                    final String name = file.getFileName().toString();
                    final String date = name.substring(0, name.length() - 4);
                    return LocalDate.parse(
                        String.format(
                            "20%s-%s-%s",
                            date.substring(0, 2),
                            date.substring(2, 4),
                            date.substring(4, 6)
                        )
                    );
                }
            ).toList();
        return points;
    }

    private static List<File> findResultFiles(final Path path, final int year) throws IOException {
        final String yearString = String.valueOf(year);
        return Files
            .list(path)
            .filter(p -> p.getFileName().toString().startsWith(yearString))
            .map(p -> p.resolve(Main.RESULT))
            .filter(p -> Files.exists(p))
            .map(Path::toFile)
            .toList();
    }

    private static List<File> findResultFiles(
        final Path path,
        final ThesisType type,
        final int year
    ) throws IOException {
        final List<File> result = new LinkedList<File>();
        switch (type) {
        case BA:
            result.addAll(Main.findResultFiles(path.resolve(Main.BACHELOR), year));
            break;
        case MA:
            result.addAll(Main.findResultFiles(path.resolve(Main.MASTER), year));
            break;
        case PA:
            result.addAll(Main.findResultFiles(path.resolve(Main.PA), year));
            break;
        case ALL_BUT_PA:
            result.addAll(Main.findResultFiles(path.resolve(Main.BACHELOR), year));
            result.addAll(Main.findResultFiles(path.resolve(Main.MASTER), year));
            break;
        default:
            result.addAll(Main.findResultFiles(path.resolve(Main.BACHELOR), year));
            result.addAll(Main.findResultFiles(path.resolve(Main.MASTER), year));
            result.addAll(Main.findResultFiles(path.resolve(Main.PA), year));
        }
        return result;
    }

    private static String getTitle(final Reviewer reviewer, final ThesisType type) {
        return String.format("%s mit %s", type.title, reviewer.title);
    }

    private static boolean isLong(final File result) {
        try {
            return Files.lines(result.toPath()).toList().contains("long");
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static boolean isNotLong(final File result) {
        return !Main.isLong(result);
    }

    private static void statistics(final File root, final Reviewer reviewer, final ThesisType type, final int year) throws IOException {
        Main.writeStatistics(
            Main.getTitle(reviewer, type),
            year,
            Main.countGrades(root, year, reviewer, type),
            root
            .toPath()
            .resolve(Main.STATISTICS)
            .resolve(Main.toStatisticsFileName(reviewer, type, year))
            .toFile()
        );
    }

    private static String toStatisticsFileName(final Reviewer reviewer, final ThesisType type, final int year) {
        return String.format(
            Main.STATISTICS_FILE,
            reviewer.name().charAt(0) + reviewer.name().substring(1).toLowerCase(),
            type.name(),
            year
        );
    }

    private static List<Thesis> toTheses(final List<File> resultFiles) {
        return Main.toTheses(resultFiles.stream());
    }

    private static List<Thesis> toTheses(final Stream<File> stream) {
        return stream.map(Thesis::fromFile).toList();
    }

    private static void writePoints(final Points points, final File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Erstbetreuung Bachelorarbeiten: ");
            writer.write(String.valueOf(points.bachelorFirst.size()));
            writer.write("\nZweitgutachten Bachelorarbeiten (lang): ");
            writer.write(String.valueOf(points.bachelorSecondLong.size()));
            writer.write("\nZweitgutachten Bachelorarbeiten (kurz): ");
            writer.write(String.valueOf(points.bachelorSecondShort.size()));
            writer.write("\nErstbetreuung Masterarbeiten: ");
            writer.write(String.valueOf(points.masterFirst.size()));
            writer.write("\nZweitgutachten Masterarbeiten (lang): ");
            writer.write(String.valueOf(points.masterSecondLong.size()));
            writer.write("\nZweitgutachten Masterarbeiten (kurz): ");
            writer.write(String.valueOf(points.masterSecondShort.size()));
            writer.write("\nErstbetreuung Praxisarbeiten: ");
            writer.write(String.valueOf(points.practicalThesesFirst.size()));
            writer.write("\nZweitgutachten Praxisarbeiten (lang): ");
            writer.write(String.valueOf(points.practicalThesesSecondLong.size()));
            writer.write("\nZweitgutachten Praxisarbeiten (kurz): ");
            writer.write(String.valueOf(points.practicalThesesSecondShort.size()));
            writer.write("\nPraxischecks: ");
            writer.write(String.valueOf(points.practicalCheck.size()));
            writer.write("\n\nSumme: ");
            writer.write(String.valueOf(points.sum()));
            writer.write("\n\n\n");
            writer.write("Details:\n\n");
            Main.writeTheses("Erstbetreuung Bachelorarbeiten", points.bachelorFirst, writer);
            Main.writeTheses("Zweitgutachten Bachelorarbeiten (lang)", points.bachelorSecondLong, writer);
            Main.writeTheses("Zweitgutachten Bachelorarbeiten (kurz)", points.bachelorSecondShort, writer);
            Main.writeTheses("Erstbetreuung Masterarbeiten", points.masterFirst, writer);
            Main.writeTheses("Zweitgutachten Masterarbeiten (lang)", points.masterSecondLong, writer);
            Main.writeTheses("Zweitgutachten Masterarbeiten (kurz)", points.masterSecondShort, writer);
            Main.writeTheses("Erstbetreuung Praxisarbeiten", points.practicalThesesFirst, writer);
            Main.writeTheses("Zweitgutachten Praxisarbeiten (lang)", points.practicalThesesSecondLong, writer);
            Main.writeTheses("Zweitgutachten Praxisarbeiten (kurz)", points.practicalThesesSecondShort, writer);
            writer.write("Praxischecks:\n");
            for (final LocalDate date : points.practicalCheck) {
                writer.write(date.toString());
                writer.write("\n");
            }
            writer.write("\n");
        }
    }

    private static void writeStatistics(
        final String title,
        final int year,
        final int[] gradeCount,
        final File file
    ) throws IOException {
        final int maxCount = Math.max(Arrays.stream(gradeCount).max().orElse(0) + 1, 6);
        double average = 0;
        int count = 0;
        for (int i = 0; i < gradeCount.length; i++) {
            count += gradeCount[i];
            average += Main.GRADES[i] * gradeCount[i];
        }
        average = average / count;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("\\documentclass[12pt]{article}\n\n");
            writer.write("\\usepackage[a4paper,landscape,margin=1cm]{geometry}\n");
            writer.write("\\usepackage{pgfplots}\n\n");
            writer.write("\\pagestyle{empty}\n\n");
            writer.write("\\begin{document}\n\n");
            writer.write("\\pgfplotstableread[row sep=\\\\,col sep=&]{\n");
            writer.write("    grade & number \\\\\n");
            writer.write(String.format("    1.0   & %d \\\\\n", gradeCount[0]));
            writer.write(String.format("    1.3   & %d \\\\\n", gradeCount[1]));
            writer.write(String.format("    1.7   & %d \\\\\n", gradeCount[2]));
            writer.write(String.format("    2.0   & %d \\\\\n", gradeCount[3]));
            writer.write(String.format("    2.3   & %d \\\\\n", gradeCount[4]));
            writer.write(String.format("    2.7   & %d \\\\\n", gradeCount[5]));
            writer.write(String.format("    3.0   & %d \\\\\n", gradeCount[6]));
            writer.write(String.format("    3.3   & %d \\\\\n", gradeCount[7]));
            writer.write(String.format("    3.7   & %d \\\\\n", gradeCount[8]));
            writer.write(String.format("    4.0   & %d \\\\\n", gradeCount[9]));
            writer.write(String.format("    5.0   & %d \\\\\n", gradeCount[10]));
            writer.write("    }\\mydata\n\n");
            writer.write("\\vspace*{2cm}\n\n");
            writer.write("\\begin{center}\n\n");
            writer.write(String.format("{\\Huge \\textbf{Notenspiegel %s Ströder %d}}\n\n", title, year));
            writer.write("\\vspace*{1cm}\n\n");
            writer.write("{\\large\n");
            writer.write("\\begin{tikzpicture}\n");
            writer.write("    \\begin{axis}[\n");
            writer.write("            ybar,\n");
            writer.write("            bar width=.5cm,\n");
            writer.write("            width=0.8\\paperwidth,\n");
            writer.write("            height=0.5\\paperheight,\n");
            writer.write("            legend style={at={(0.5,1)},\n");
            writer.write("                anchor=north,legend columns=-1},\n");
            writer.write("            symbolic x coords={1.0,1.3,1.7,2.0,2.3,2.7,3.0,3.3,3.7,4.0,5.0},\n");
            writer.write("            xtick=data,\n");
            writer.write("            nodes near coords,\n");
            writer.write("            nodes near coords align={vertical},\n");
            writer.write(String.format("            ymin=0,ymax=%d,\n", maxCount));
            writer.write("            ylabel={Anzahl},\n");
            writer.write("            x label style={at={(axis description cs:0.5,-0.05)},anchor=north},\n");
            writer.write("            xlabel={Note}\n");
            writer.write("        ]\n");
            writer.write("        \\addplot table[x=grade,y=number]{\\mydata};\n");
            writer.write("    \\end{axis}\n");
            writer.write("\\end{tikzpicture}\n");
            writer.write("}\n\n");
            writer.write("\\vspace*{8mm}\n\n");
            writer.write(String.format(Locale.GERMAN, "Notendurchschnitt: %.1f\n\n", average));
            writer.write("\\end{center}\n\n");
            writer.write("\\end{document}\n");
        }
    }

    private static void writeTheses(final String section, final List<Thesis> theses, final BufferedWriter writer)
    throws IOException {
        writer.write(section);
        writer.write(":\n");
        for (final Thesis thesis : theses) {
            writer.write(thesis.name());
            writer.write(": ");
            writer.write(thesis.title());
            writer.write("\n");
        }
        writer.write("\n");
    }

}

package thesesstats.templates;

import java.io.*;
import java.util.*;

public abstract class ReviewTemplate {

    public boolean isOlderVersion(final String version) {
        final String type = version.substring(0, 3);
        final String currentVersion = this.version();
        final String currentType = currentVersion.substring(0, 3);
        if (!type.equals(currentType)) {
            return false;
        }
        final String[] versionNumber = version.substring(3).split("\\.");
        final String[] currentVersionNumber = currentVersion.substring(3).split("\\.");
        int i = 0;
        while (i < versionNumber.length && i < currentVersionNumber.length) {
            final int compare =
                Integer.compare(Integer.parseInt(versionNumber[i]), Integer.parseInt(currentVersionNumber[i]));
            if (compare < 0) {
                return true;
            } else if (compare > 0) {
                return false;
            }
            i++;
        }
        if (versionNumber.length < currentVersionNumber.length) {
            while (i < currentVersionNumber.length) {
                if (Integer.parseInt(currentVersionNumber[i]) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void writeTemplate(
        final String author,
        final String title,
        final Optional<String> otherReviewer,
        final BufferedWriter writer
    ) throws IOException {
        writer.write("%version: ");
        writer.write(this.version());
        writer.write("\n%empty\n");
        writer.write("\\documentclass{article}\n\n");
        writer.write("\\input{../../../../../templates/review/packages.tex}\n\n");
        writer.write("\\newcommand{\\thesistype}{");
        writer.write(this.thesisType());
        writer.write("}\n");
        writer.write("\\newcommand{\\thesistitle}{");
        writer.write(title);
        writer.write("}\n");
        writer.write("\\newcommand{\\thesisauthor}{");
        writer.write(author);
        writer.write("}\n");
        writer.write("\\newcommand{\\reviewdate}{\\today}\n");
        writer.write("\\newcommand{\\reviewplace}{Essen}\n");
        writer.write("\\newcommand{\\signaturepath}{../../../../../Bilder/signature.png}\n");
        writer.write("\\setboolean{restrictionnote}{false}\n");
        writer.write("\\setboolean{pagebreakmiddle}{true}\n");
        writer.write("\\setboolean{pagebreaktotal}{false}\n");
        writer.write("\\setboolean{tworeviewers}{false}\n");
        writer.write("\\newcommand{\\otherreviewer}{");
        if (otherReviewer.isPresent()) {
            writer.write(otherReviewer.get());
        }
        writer.write("}\n");
        writer.write("\n\\newcommand{\\structureReview}{%\n");
        this.writeStructureTemplate(writer);
        writer.write("}\n\n");
        writer.write("\\newcommand{\\methodsReview}{%\n");
        this.writeMethodsTemplate(writer);
        writer.write("}\n\n");
        writer.write("\\newcommand{\\contentReview}{%\n");
        this.writeContentTemplate(writer);
        writer.write("}\n\n");
        writer.write("\\newcommand{\\formalReview}{%\n");
        this.writeFormalTemplate(writer);
        writer.write("}\n\n");
        writer.write("\\newcommand{\\totalReview}{%\n");
        writer.write(
            "Insgesamt wurden \\evaluationpoints{} Punkte erreicht und das Gesamturteil lautet: \\grade\n"
        );
        writer.write("}\n\n");
        writer.write("\\input{../../../../../templates/review/review.tex}\n");
    }

    protected abstract String version();

    abstract String thesisType();

    void writeContentTemplate(final BufferedWriter writer) throws IOException {
        writer.write("Die Arbeit liefert die folgenden inhaltlichen Beiträge:\n");
        writer.write("\\begin{itemize}\n");
        writer.write("\\item Beitrag 1\n");
        writer.write("\\end{itemize}\n");
        writer.write("\\innovativenessvi{}\n");
        writer.write("\\relevancevi{}\n");
        writer.write("\\levelvi{}\n");
        writer.write("\\applicabilityvi{}\n");
        writer.write("\\valuevi{}\n");
        writer.write("\\evaluationpartresult{30}\n");
    }

    abstract void writeFormalTemplate(BufferedWriter writer) throws IOException;

    abstract void writeMethodsTemplate(BufferedWriter writer) throws IOException;

    abstract void writeStructureTemplate(BufferedWriter writer) throws IOException;

}

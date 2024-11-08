package thesesstats.templates;

import java.io.*;

public class ReviewTemplateBachelor extends ReviewTemplate {

    @Override
    String thesisType() {
        return "Bachelorarbeit";
    }

    @Override
    void writeFormalTemplate(final BufferedWriter writer) throws IOException {
        writer.write("\\distancesi{}\n");
        writer.write("\\spellingautoiii{}\n");
        writer.write("\\spellinggrammarii{}\n");
        writer.write("\\spellingpunctuationi{}\n");
        writer.write("\\languageii{}\n");
        writer.write("\\illustrationsiv{}\n");
        writer.write("\\figuresqualityii{}\n");
        writer.write("\\quotingstylei{}\n");
        writer.write("\\quotinglookupi{}\n");
        writer.write("\\literaturestylei{}\n");
        writer.write("\\literaturepropsii{}\n");
        writer.write("\\diligenceiii{}\n");
        writer.write("\\evaluationpartresult{20}\n");
    }

    @Override
    void writeMethodsTemplate(final BufferedWriter writer) throws IOException {
        writer.write("\\literatureamountiii{}\n");
        writer.write("\\literaturequalityiii{}\n");
        writer.write("\\quotingdensityiii{}\n");
        writer.write("\\relatedamountiii{}\n");
        writer.write("\\relatedqualityiii{}\n");
        writer.write("\\methodapplicationiii{}\n");
        writer.write("\\methodintroiii{}\n");
        writer.write("\\objectivityii{}\n");
        writer.write("\\reliabilityii{}\n");
        writer.write("\\validityii{}\n");
        writer.write("\\comprehensibilityiii{}\n");
        writer.write("\\evaluationpartresult{30}\n");
    }

    @Override
    void writeStructureTemplate(final BufferedWriter writer) throws IOException {
        writer.write("\\appearancei{}\n");
        writer.write("\\tocqualityii{}\n");
        writer.write("\\listsi{}\n");
        writer.write("\\goaliii{}\n");
        writer.write("\\contributionsii{}\n");
        writer.write("\\methodoverviewi{}\n");
        writer.write("\\structurequalityiii{}\n");
        writer.write("\\referencesi{}\n");
        writer.write("\\conceptsintroducedi{}\n");
        writer.write("\\basicsusedi{}\n");
        writer.write("\\futureii{}\n");
        writer.write("\\conclusionii{}\n");
        writer.write("\\evaluationpartresult{20}\n");
    }

}

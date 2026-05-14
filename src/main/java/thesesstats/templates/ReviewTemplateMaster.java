package thesesstats.templates;

import java.io.*;

public class ReviewTemplateMaster extends ReviewTemplate {

    @Override
    protected String version() {
        return "MA 3.0";
    }

    @Override
    String thesisType() {
        return "Masterarbeit";
    }

    @Override
    void writeFormalTemplate(final BufferedWriter writer) throws IOException {
        writer.write("\\distancesi{}\n");
        writer.write("\\spellingautoii{}\n");
        writer.write("\\spellinggrammari{}\n");
        writer.write("\\spellingpunctuationi{}\n");
        writer.write("\\languagei{}\n");
        writer.write("\\illustrationiv{}\n");
        writer.write("\\figuresqualityi{}\n");
        writer.write("\\quotingstylei{}\n");
        writer.write("\\quotinglookupi{}\n");
        writer.write("\\literaturestylei{}\n");
        writer.write("\\literaturepropsi{}\n");
        writer.write("\\diligenceiii{}\n");
        writer.write("\\evaluationpartresult{15}\n");
    }

    @Override
    void writeMethodsTemplate(final BufferedWriter writer) throws IOException {
        writer.write("\\literatureamountiv{}\n");
        writer.write("\\literaturequalityiv{}\n");
        writer.write("\\quotingapplicationiv{}\n");
        writer.write("\\relatedlookupi{}\n");
        writer.write("\\relatedamountiv{}\n");
        writer.write("\\relatedcontenti{}\n");
        writer.write("\\relateddifferencei{}\n");
        writer.write("\\relatednewi{}\n");
        writer.write("\\methodintroi{}\n");
        writer.write("\\methodreasoni{}\n");
        writer.write("\\methodcoveriii{}\n");
        writer.write("\\methodapplicationiv{}\n");
        writer.write("\\objectivityii{}\n");
        writer.write("\\reliabilityii{}\n");
        writer.write("\\validityiv{}\n");
        writer.write("\\comprehensibilityiv{}\n");
        writer.write("\\evaluationpartresult{40}\n");
    }

    @Override
    void writeStructureTemplate(final BufferedWriter writer) throws IOException {
        writer.write("\\appearancei{}\n");
        writer.write("\\tocqualityi{}\n");
        writer.write("\\listsi{}\n");
        writer.write("\\goalii{}\n");
        writer.write("\\contributionsii{}\n");
        writer.write("\\methodoverviewi{}\n");
        writer.write("\\structurequalityii{}\n");
        writer.write("\\referencesi{}\n");
        writer.write("\\conceptsintroducedi{}\n");
        writer.write("\\basicsusedi{}\n");
        writer.write("\\futurei{}\n");
        writer.write("\\conclusioni{}\n");
        writer.write("\\evaluationpartresult{15}\n");
    }

}

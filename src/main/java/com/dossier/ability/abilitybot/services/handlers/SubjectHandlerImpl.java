package com.dossier.ability.abilitybot.services.handlers;

import com.dossier.ability.abilitybot.domain.Subject;
import com.dossier.ability.abilitybot.services.parsers.UriParser;
import com.dossier.ability.abilitybot.utils.SubjectUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectHandlerImpl implements SubjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectHandlerImpl.class);

    private final static String SPLITTER_SYMBOL = ".|;";

    private final UriParser uriParser;

    @Autowired
    public SubjectHandlerImpl(UriParser uriParser) {
        this.uriParser = uriParser;
    }

    @Override
    public List<Subject> getDossierSubjectsByUri(URI pdfUri) {
        return getDossierSubjectsFromUri(pdfUri);
    }

    private List<Subject> getDossierSubjectsFromUri(URI pdfUri)  {
        List<Subject> subjects = new ArrayList<>();
        try (PDDocument document = PDDocument.load(getInputStreamFromUri(pdfUri))) {
            if (!document.isEncrypted()) {
                LOGGER.info("Processing: {}", pdfUri);
                for (String candidate : getArrayOfStringFromPdf(document)) {
                    addSubjectFromStringToList(subjects, candidate, pdfUri.toString());
                }
                return subjects;
            }
        } catch (IOException e) {
            LOGGER.debug("Failed while processing: {}", pdfUri);
        }

        return subjects;
    }

    private void addSubjectFromStringToList(List<Subject> subjects, String candidate, String pdfUri) {
        if (SubjectUtils.isStringMatched(candidate, SubjectUtils.REGEX_MATCHER_FOR_DOSSIER)) {
            Subject obtainedSubject = SubjectUtils.getSubjectFromString(candidate);
            obtainedSubject.setPdfUri(pdfUri);

            subjects.add(obtainedSubject);
        }
    }

    private String[] getArrayOfStringFromPdf(PDDocument document) throws IOException {
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        return new PDFTextStripper().getText(document).split(SPLITTER_SYMBOL);
    }

    private InputStream getInputStreamFromUri(URI pdfUri) {
        return uriParser.getStreamFromPdfUri(pdfUri, HttpClientBuilder.create().build());
    }
}

package ru.jegensomme.homeaccountant.web.validation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/** Based on Hibernate jsoup 1.13.1 SafeHtmlValidator */
public class SafeHtmlValidator implements ConstraintValidator<SafeHtml, CharSequence> {

    @Override
    public void initialize(SafeHtml constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value == null || new Cleaner(Whitelist.none()).isValid(getFragmentAsDocument(value));
    }

    private Document getFragmentAsDocument(CharSequence value) {
        Document fragment = Jsoup.parse(value.toString(), "", Parser.xmlParser());
        Document document = Document.createShell("");
        List<Node> childNodes = fragment.childNodes();
        childNodes.forEach(n -> document.body().appendChild(n.clone()));
        return document;
    }
}

package com.literandltx.assignment.service.parser;

import com.literandltx.assignment.exception.custom.FileParseException;
import com.literandltx.assignment.model.Animal;
import org.springframework.stereotype.Component;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Component
public class XmlParser implements Parser {
    private static final String LIST_NAME = "animals";
    private static final String ITEM_NAME = "animal";

    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String TAG_SEX = "sex";
    private static final String TAG_WEIGHT = "weight";
    private static final String TAG_COST = "cost";

    @Override
    public List<Animal> parseToAnimals(final File file) {
        final List<Animal> animals = new ArrayList<>();

        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document document = builder.parse(file);
            final Element root = document.getDocumentElement();

            if (LIST_NAME.equals(root.getTagName())) {
                final NodeList nodes = root.getElementsByTagName(ITEM_NAME);

                for (int i = 0; i < nodes.getLength(); i++) {
                    final Node node = nodes.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        final Element animalElement = (Element) node;
                        final Optional<Animal> animal = parseAnimalFromElement(animalElement);

                        animal.ifPresent(animals::add);
                    }
                }
            }
        } catch (final ParserConfigurationException | SAXException | IOException e) {
            throw new FileParseException("Cannot parser .xml file: " + file.getName(), e);
        }

        return animals;
    }

    private Optional<Animal> parseAnimalFromElement(final Element itemElement) {
        final Optional<String> name = getStringValue(itemElement, TAG_NAME);
        final Optional<String> type = getStringValue(itemElement, TAG_TYPE);
        final Optional<String> sexString = getStringValue(itemElement, TAG_SEX);
        final Optional<Integer> weight = getIntValue(itemElement, TAG_WEIGHT);
        final Optional<Integer> cost = getIntValue(itemElement, TAG_COST);

        if (name.isEmpty() || type.isEmpty() || sexString.isEmpty() || weight.isEmpty() || cost.isEmpty()) {
            return Optional.empty();
        }

        final Optional<Animal.Sex> sex = Animal.Sex.fromString(sexString.get());
        final Optional<Animal.Category> category = Animal.Category.getCategory(weight.get());

        if (sex.isPresent() && category.isPresent()) {
            return Optional.of(new Animal(name.get(), type.get(), sex.get(), category.get(), weight.get(), cost.get()));
        }

        return Optional.empty();
    }

    private Optional<String> getStringValue(
            final Element element,
            final String tagName
    ) {
        final NodeList nodeList = element.getElementsByTagName(tagName);

        if (nodeList.getLength() > 0) {
            return Optional.ofNullable(nodeList.item(0).getTextContent());
        }

        return Optional.empty();
    }

    private Optional<Integer> getIntValue(
            final Element element,
            final String tagName
    ) {
        final NodeList nodeList = element.getElementsByTagName(tagName);

        if (nodeList.getLength() > 0) {
            return Optional.of(Integer.parseInt(nodeList.item(0).getTextContent().trim()));
        }

        return Optional.empty();
    }

}

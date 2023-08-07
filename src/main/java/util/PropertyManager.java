package util;

import de.dhbwka.swe.utils.gui.IConfigurableProperties;
import de.dhbwka.swe.utils.util.IPropertyManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PropertyManager implements IPropertyManager {

    @Override
    public void setProperty(String s, String s1) {

    }

    @Override
    public <T> void setProperties(String s, T[] ts) {

    }

    @Override
    public <T> void setProperties(String s, List<T> list) {

    }

    @Override
    public <T> void setPropertyArray(String s, T[] ts) {

    }

    @Override
    public String[] getProperties(String s) {
        return new String[0];
    }

    @Override
    public String[] getProperties(String s, Optional<String[]> optional) {
        return new String[0];
    }

    @Override
    public String[] getProperties(String s, Optional<String[]> optional, boolean b) {
        return new String[0];
    }

    @Override
    public String getProperty(String s) {
        return null;
    }

    @Override
    public <T> T getTypedProperty(String s, Class<T> aClass) throws NumberFormatException {
        return null;
    }

    @Override
    public String getProperty(IConfigurableProperties iConfigurableProperties) {
        return null;
    }

    @Override
    public <T> T getTypedProperty(String s, Class<T> aClass, Optional<T> optional) {
        return null;
    }

    @Override
    public <T> T getTypedProperty(String s, Class<T> aClass, Optional<T> optional, boolean b) {
        return null;
    }

    @Override
    public String getProperty(String s, Optional<String> optional) {
        return null;
    }

    @Override
    public String getProperty(String s, Optional<String> optional, boolean b) {
        return null;
    }

    @Override
    public void printout(PrintStream printStream) {

    }

    @Override
    public void printout(PrintStream printStream, boolean b) {

    }

    @Override
    public String getPropertiesFileName() {
        return null;
    }

    @Override
    public void saveConfiguration() throws IOException {

    }

    @Override
    public void saveConfiguration(String s, Optional<String> optional) throws IOException {

    }

    @Override
    public void saveConfiguration(OutputStream outputStream, Optional<String> optional) throws IOException {

    }

    @Override
    public String removeProperty(String s) {
        return null;
    }

    @Override
    public void removeProperties(String s) {

    }

    @Override
    public void addProperties(HashMap<String, String> hashMap) {

    }

    @Override
    public <K> void removeProperties(Set<K> set) {

    }

    @Override
    public List<String> containsProperties(List<String> list) {
        return null;
    }
}

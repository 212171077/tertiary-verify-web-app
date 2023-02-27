/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import com.pc.constants.AppConstants;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christoph Sibiya
 */
public class CRUD {

    private static String appPath = "";
    private static String templatesPath = "";
    private static String repoPath = "";
    private static String servicePath = "";
    private static String uiPath = "";
    private static String convertorPath = "";
    private static String commonItemUIPath = "";
    private static String[] ex = {"getError", "getClass", "getRowCount", "getList", "getId"};
    private static String entityPackage = "";
    private static String hxhtmlLookupPath = "";

    public static void createRepo(String entity) {
        String type = "repo";
        try {
            String templateData = IOUtils.readFile(templatesPath + type.toLowerCase() + ".txt");
            templateData = templateData.replaceAll("####", entity);
            templateData = templateData.replaceAll("%%%%", decapitalize(entity));
            IOUtils.writeToFile(repoPath + entity + "Repository" + ".java", new StringBuffer(templateData));
            System.out.println("Repository Created Successful...");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void createService(String entity) {
        String type = "service";
        try {
            String templateData = IOUtils.readFile(templatesPath + type.toLowerCase() + ".txt");
            templateData = templateData.replaceAll("####", entity);
            templateData = templateData.replaceAll("%%%%", decapitalize(entity));
            IOUtils.writeToFile(servicePath + entity + "Service" + ".java", new StringBuffer(templateData));
            System.out.println("Service Created Successful...");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void createUI(String entity) {
        String type = "ui";
        try {
            String templateData = IOUtils.readFile(templatesPath + type.toLowerCase() + ".txt");
            templateData = templateData.replaceAll("####", entity);
            templateData = templateData.replaceAll("%%%%", decapitalize(entity));
            IOUtils.writeToFile(uiPath + entity + "UI" + ".java", new StringBuffer(templateData));
            System.out.println("UI Created Successful...");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void createConvertor(String entity) {
        String type = "convertor";
        try {
            String templateData = IOUtils.readFile(templatesPath + type.toLowerCase() + ".txt");
            templateData = templateData.replaceAll("####", entity);
            templateData = templateData.replaceAll("%%%%", decapitalize(entity));
            IOUtils.writeToFile(convertorPath + entity + "Convertor" + ".java", new StringBuffer(templateData));
            System.out.println("Convertor Created Successful...");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void createSelectAutoCompleteMethod(String entity) {
        String type = "select_auto_complete";
        try {
            String templateData = IOUtils.readFile(templatesPath + type.toLowerCase() + ".txt");
            templateData = templateData.replaceAll("####", entity);
            templateData = templateData.replaceAll("%%%%", decapitalize(entity));
            String commonItemUIData = IOUtils.readFile(commonItemUIPath + "CommonItemUI.java");
            commonItemUIData = commonItemUIData.replaceAll("//===============DO NOT REMOVE THIS=================//", templateData);
            IOUtils.writeToFile(commonItemUIPath + "CommonItemUI.java", new StringBuffer(commonItemUIData));
            System.out.println("Select And Auto Complete Methods Added Successful...");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void createXHTML(String entity) {
        try {
            String imputField = "";
            String tableField = "";
            List<String> l = reflectBean(entity);
            for (String field : l) {
                if (!field.contentEquals("createDate") && !field.contentEquals("lastUpdateDate") && !field.contentEquals("lastUpdateUser")) {

                    String inputFieldsTemplateData = IOUtils.readFile(templatesPath + "input_fields.txt");
                    String tableFieldsTemplateData = IOUtils.readFile(templatesPath + "table_fields.txt");
                    if (field.contains("Lookup")) {
                        inputFieldsTemplateData = IOUtils.readFile(templatesPath + "auto_complete.txt");
                        inputFieldsTemplateData = inputFieldsTemplateData.replaceAll("%%##", getLookupName(field));//autoComplete
                        inputFieldsTemplateData = inputFieldsTemplateData.replaceAll("%%%#", decapitalize(getLookupName(field)));//Convertor
                    } else if (field.contains("Enum")) {
                        createSelectEnumMethod(capitalize(field));
                        inputFieldsTemplateData = IOUtils.readFile(templatesPath + "enum_select_one_menu.txt");
                        inputFieldsTemplateData = inputFieldsTemplateData.replaceAll("%%%#", decapitalize(field));

                    } else if (field.contains("Date")) {
                        createSelectEnumMethod(capitalize(field));
                        inputFieldsTemplateData = IOUtils.readFile(templatesPath + "date_input_field.txt");
                    }

                    inputFieldsTemplateData = inputFieldsTemplateData.replaceAll("%##%", field);
                    inputFieldsTemplateData = inputFieldsTemplateData.replaceAll("#%#%", getFieldDescription(field));
                    inputFieldsTemplateData = inputFieldsTemplateData.replaceAll("%%%%", decapitalize(entity));
                    inputFieldsTemplateData = inputFieldsTemplateData.replaceAll("####", entity);

                    tableFieldsTemplateData = tableFieldsTemplateData.replaceAll("%##%", field);
                    tableFieldsTemplateData = tableFieldsTemplateData.replaceAll("#%#%", getFieldDescription(field));

                    imputField += inputFieldsTemplateData + "\n";
                    tableField += tableFieldsTemplateData + "\n";
                }

            }
            String type = "xhtml";
            String templateData = IOUtils.readFile(templatesPath + type.toLowerCase() + ".txt");
            templateData = templateData.replaceAll("####", entity);
            templateData = templateData.replaceAll("#%#%", getFieldDescription(entity));
            templateData = templateData.replaceAll("%%%%", decapitalize(entity));
            templateData = templateData.replaceAll("##INPUT_FIELDS##", imputField);
            templateData = templateData.replaceAll("##TABLE_FIELDS##", tableField);
            IOUtils.writeToFile(hxhtmlLookupPath + entity.toLowerCase() + ".xhtml", new StringBuffer(templateData));
            System.out.println("XHTML Created Successful...");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getLookupName(String field) {
        String lpName = "";
        lpName = field.replace("Lookup", "");
        lpName = capitalize(lpName);
        return lpName;
    }

    public static String getFieldDescription(String word) {
        word = word.replace("Lookup", "");
        word = word.replace("Enum", "");
        String fieldDesc = capitalizeEarchWord(spiltWordByupperCase(word));
        return fieldDesc;
    }

    /**
     * Input: my name is chris
     * Output: My Name Is Chris
     */
    public static String capitalizeEarchWord(String str) {
        String capitalizeWord = "";
        if (str != null && str.length() > 0) {
            String[] words = str.split("\\s");
            for (String w : words) {
                String first = w.substring(0, 1);
                String afterfirst = w.substring(1);
                capitalizeWord += first.toUpperCase() + afterfirst + " ";
            }
        } else {
            capitalizeWord = str;
        }
        return capitalizeWord.trim();
    }

    /**
     * Input: thisIsMyString
     * Output:this Is My String
     */
    public static String spiltWordByupperCase(String input) {
        String output = "";
        if (input != null && input.length() > 0) {
            String[] elements = input.split("(?=\\p{Upper})");
            if (elements.length > 0) {
                for (String word : elements) {
                    output += word + " ";
                }
            } else {
                output = input;
            }
        } else {
            output = input;
        }
        return output.trim();
    }

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char[] c = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public static String capitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char[] c = string.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }

    private static List<String> reflectBean(String entity) throws Exception {
        String cl = entityPackage + "." + entity;
        Class o;
        List<String> l = new ArrayList<String>();

        o = Class.forName(cl);

        Method[] methods = o.getMethods();
        for (Method m : methods) {

            if (m.getName().charAt(0) == 'g') {
                Class x = m.getReturnType();
                if (!"java.lang.Class".equals(x.getName()) && "Id".equals(x.getName().substring(x.getName().length() - 2))) {
                    l = doComplexId(l, x);
                } else {
                    if (!ignore(m.getName().trim())) {
                        l.add(formatFld(m.getName()));
                    }
                }
            }
        }


        return l;
    }

    private static String formatFld(String fld) {
        fld = fld.substring(3, 4).toLowerCase() + fld.substring(4);
        return fld;
    }

    private static boolean ignore(String s) {
        boolean ret = false;
        if (s != null) {
            if (s.endsWith("DD")) ret = true;
            else {

                for (String a : ex) {
                    if (s.equalsIgnoreCase(a)) {
                        ret = true;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    private static List<String> doComplexId(List<String> l, Class x) throws Exception {
        Method[] methods = x.getMethods();
        for (Method m : methods) {
            if (m.getName().charAt(0) == 'g') {
                if (!ignore(m.getName().trim())) {
                    l.add(formatFld(m.getName()));
                }
            }
        }

        return l;
    }

    public static void createSelectEnumMethod(String enumName) {
        String type = "select_enum";
        try {
            String templateData = IOUtils.readFile(templatesPath + type.toLowerCase() + ".txt");
            templateData = templateData.replaceAll("####", enumName);
            String commonItemUIData = IOUtils.readFile(commonItemUIPath + "CommonItemUI.java");
            if (!commonItemUIData.contains(enumName)) {
                commonItemUIData = commonItemUIData.replaceAll("//===============DO NOT REMOVE THIS=================//", templateData);
                IOUtils.writeToFile(commonItemUIPath + "CommonItemUI.java", new StringBuffer(commonItemUIData));
                System.out.println("Select Enum And Methods Added Successful...");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void initLookupParams() {
        ///C:/Users/Christoph%20Sibiya/Documents/tertiaryverifywebappTG/target/classes/
        AppConstants appConstants = new AppConstants();
        appPath = appConstants.realpath.replace("/target/classes/", "");
        appPath = appPath.replace("%20", " ");
        templatesPath = "" + appPath + "/src/main/java/com/pc/lookup/templates/";
        repoPath = "" + appPath + "/src/main/java/com/pc/repositories/lookup/";
        servicePath = "" + appPath + "/src/main/java/com/pc/service/lookup/";
        uiPath = "" + appPath + "/src/main/java/com/pc/ui/lookup/";
        convertorPath = "" + appPath + "/src/main/java/com/pc/converter/";
        commonItemUIPath = "" + appPath + "/src/main/java/com/pc/ui/";
        String[] ex = {"getError", "getClass", "getRowCount", "getList", "getId"};
        entityPackage = "com.pc.entities.lookup";
        hxhtmlLookupPath = "" + appPath + "/src/main/webapp/admin/lookups/";

    }

    public static void initEntityParams() {
        ///C:/Users/Christoph%20Sibiya/Documents/tertiaryverifywebappTG/target/classes/
        AppConstants appConstants = new AppConstants();
        appPath = appConstants.realpath.replace("/target/classes/", "");
        appPath = appPath.replace("%20", " ");
        templatesPath = "" + appPath + "/src/main/java/com/pc/entity/templates/";
        repoPath = "" + appPath + "/src/main/java/com/pc/repositories/";
        servicePath = "" + appPath + "/src/main/java/com/pc/service/";
        uiPath = "" + appPath + "/src/main/java/com/pc/ui/";
        convertorPath = "" + appPath + "/src/main/java/com/pc/converter/";
        commonItemUIPath = "" + appPath + "/src/main/java/com/pc/ui/";
        String[] ex = {"getError", "getClass", "getRowCount", "getList", "getId"};
        entityPackage = "com.pc.entities";
        hxhtmlLookupPath = "" + appPath + "/src/main/webapp/user/crud/";

    }

    public void generateCRUD(String entity, boolean lookups, boolean createUI, boolean createService, boolean createConvertor, boolean createRepo, boolean createXhtml, boolean createSelectAutoComplete) {
        try {

            if (entity.isEmpty() || entity == null || entity.equals("")) {
                throw new Exception("Provide Entity Name");
            }
            if (lookups) {
                initLookupParams();

            } else {
                initEntityParams();
            }

            if (createRepo) {
                createRepo(entity);
            }
            if (createService) {
                createService(entity);
            }
            if (createUI) {
                createUI(entity);
            }
            if (createConvertor) {
                createConvertor(entity);
            }
            if (createSelectAutoComplete) {
                createSelectAutoCompleteMethod(entity);
            }
            if (createXhtml) {
                createXHTML(entity);
            }

            JOptionPane.showMessageDialog(null, "Done...!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }
}

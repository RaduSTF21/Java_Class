package org.example;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ReportCommand implements Command {
    @Override
    public void execute(String[] args, Repository repository)throws CommandException {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
            cfg.setClassForTemplateLoading(ReportCommand.class, "/templates");
            Template template = cfg.getTemplate("report.ftl");
            Map<String,Object> data = new HashMap<>();
            data.put("images", repository.getImages());
            File repFile = new File("report.html");
            try(Writer out = new FileWriter(repFile)){
                template.process(data, out);
            }
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(repFile);
                } else {
                    System.out.println("Desktop actions not supported. Please open " + repFile.getAbsolutePath() + " manually.");
                }
            } else {
                System.out.println("Desktop is not supported on this environment. Please open " + repFile.getAbsolutePath() + " manually.");
            }


            System.out.println("Report generated and opened");
        }
        catch (Exception e) {
            throw new CommandException("Error generating report: " + e.getMessage());
        }
    }
}

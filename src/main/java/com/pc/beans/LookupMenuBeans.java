package com.pc.beans;

public class LookupMenuBeans {

    private String link;
    private String heading;

    public LookupMenuBeans() {
        super();
        // TODO Auto-generated constructor stub
    }

    public LookupMenuBeans(String link, String heading) {
        super();
        this.link = link;
        this.heading = heading;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "LookupMenuBeans{" +
                "link='" + link + '\'' +
                ", heading='" + heading + '\'' +
                '}';
    }
}

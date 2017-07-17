package com.MinimalSoft.Joiin.Responses;

public class FacebookData {
    private String id;
    private String name;
    private String first_name;
    private String last_name;
    private String birthday;
    private String gender;
    private String email;
    private Cover cover;
    private Picture picture;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return name;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cover getCover() {
        return cover;
    }

    public Picture getPicture() {
        return picture;
    }

    public class Cover {
        private String source;
        private String id;
        private int offsetY;

        public String getSource() {
            return source;
        }

        public String getId() {
            return id;
        }

        public int getOffsetY() {
            return offsetY;
        }
    }

    public class Picture {
        private Data data;

        public Data getData() {
            return data;
        }

        public class Data {
            private boolean isSilhouette;
            private String url;

            public boolean isSilhouette() {
                return isSilhouette;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
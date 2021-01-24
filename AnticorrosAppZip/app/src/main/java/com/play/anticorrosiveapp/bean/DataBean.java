package com.play.anticorrosiveapp.bean;

import java.util.List;

public  class DataBean {
        private List<AdListBean> ad_list;
        private List<CompanyListBean> company_list;
        private List<NewsListBean> news_list;

        public List<AdListBean> getAd_list() {
            return ad_list;
        }

        public void setAd_list(List<AdListBean> ad_list) {
            this.ad_list = ad_list;
        }

        public List<CompanyListBean> getCompany_list() {
            return company_list;
        }

        public void setCompany_list(List<CompanyListBean> company_list) {
            this.company_list = company_list;
        }

        public List<NewsListBean> getNews_list() {
            return news_list;
        }

        public void setNews_list(List<NewsListBean> news_list) {
            this.news_list = news_list;
        }

        public static class AdListBean {
            /**
             * image : http://resource.ffu365.com/upload/images/default/2016-06-04/57529302a2f602.54526763.jpg
             * link : http://m.ffu365.com/static/bas/1.html
             */

            private String image;
            private String link;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class CompanyListBean {
            /**
             * image : http://resource.ffu365.com/upload/images/default/2016-06-02/574fd1fb4b91d8.93153953.jpg
             * link : http://app.ffu365.com/pages/company.html
             */

            private String image;
            private String link;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class NewsListBean {
            /**
             * title : 天天防腐APP产品定位及功能介绍
             * create_time : 2016-05-01
             * link : http://m.ffu365.com/static/News/news.html?id=10
             */

            private String title;
            private String create_time;
            private String link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
package tech.shunzi.testdev.utils.test;

import java.util.List;

public class A {
    private int code;
    private String message;
    private Data data;

    public static class Data {
        private int code;
        private int pageIndex;
        private int total;
        private List<DataList> data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataList> getData() {
            return data;
        }

        public void setData(List<DataList> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "code=" + code +
                    ", pageIndex=" + pageIndex +
                    ", total=" + total +
                    ", data=" + data +
                    '}';
        }

        public static class DataList {
            private String logId;
            private String id;
            private int isDeleted;
            private String addTime;
            private String addAccountId;
            private String addAccountName;
            private String editTime;
            private String editAccountId;
            private String editAccountName;
            private String organizationId;
            private String organizationCode;
            private String organizationName;
            private String topOrganizationId;
            private String topOrganizationCode;
            private String topOrganizationName;
            private String parentId;
            private String contacts;
            private String contactsPhone;
            private int accountSource;
            private int auditState;
            private int state;
            private String auditAccountId;
            private String auditAccountName;
            private String auditTime;
            private String auditReason;

            public String getLogId() {
                return logId;
            }

            public void setLogId(String logId) {
                this.logId = logId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(int isDeleted) {
                this.isDeleted = isDeleted;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getAddAccountId() {
                return addAccountId;
            }

            public void setAddAccountId(String addAccountId) {
                this.addAccountId = addAccountId;
            }

            public String getAddAccountName() {
                return addAccountName;
            }

            public void setAddAccountName(String addAccountName) {
                this.addAccountName = addAccountName;
            }

            public String getEditTime() {
                return editTime;
            }

            public void setEditTime(String editTime) {
                this.editTime = editTime;
            }

            public String getEditAccountId() {
                return editAccountId;
            }

            public void setEditAccountId(String editAccountId) {
                this.editAccountId = editAccountId;
            }

            public String getEditAccountName() {
                return editAccountName;
            }

            public void setEditAccountName(String editAccountName) {
                this.editAccountName = editAccountName;
            }

            public String getOrganizationId() {
                return organizationId;
            }

            public void setOrganizationId(String organizationId) {
                this.organizationId = organizationId;
            }

            public String getOrganizationCode() {
                return organizationCode;
            }

            public void setOrganizationCode(String organizationCode) {
                this.organizationCode = organizationCode;
            }

            public String getOrganizationName() {
                return organizationName;
            }

            public void setOrganizationName(String organizationName) {
                this.organizationName = organizationName;
            }

            public String getTopOrganizationId() {
                return topOrganizationId;
            }

            public void setTopOrganizationId(String topOrganizationId) {
                this.topOrganizationId = topOrganizationId;
            }

            public String getTopOrganizationCode() {
                return topOrganizationCode;
            }

            public void setTopOrganizationCode(String topOrganizationCode) {
                this.topOrganizationCode = topOrganizationCode;
            }

            public String getTopOrganizationName() {
                return topOrganizationName;
            }

            public void setTopOrganizationName(String topOrganizationName) {
                this.topOrganizationName = topOrganizationName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getContacts() {
                return contacts;
            }

            public void setContacts(String contacts) {
                this.contacts = contacts;
            }

            public String getContactsPhone() {
                return contactsPhone;
            }

            public void setContactsPhone(String contactsPhone) {
                this.contactsPhone = contactsPhone;
            }

            public int getAccountSource() {
                return accountSource;
            }

            public void setAccountSource(int accountSource) {
                this.accountSource = accountSource;
            }

            public int getAuditState() {
                return auditState;
            }

            public void setAuditState(int auditState) {
                this.auditState = auditState;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getAuditAccountId() {
                return auditAccountId;
            }

            public void setAuditAccountId(String auditAccountId) {
                this.auditAccountId = auditAccountId;
            }

            public String getAuditAccountName() {
                return auditAccountName;
            }

            public void setAuditAccountName(String auditAccountName) {
                this.auditAccountName = auditAccountName;
            }

            public String getAuditTime() {
                return auditTime;
            }

            public void setAuditTime(String auditTime) {
                this.auditTime = auditTime;
            }

            public String getAuditReason() {
                return auditReason;
            }

            public void setAuditReason(String auditReason) {
                this.auditReason = auditReason;
            }

            @Override
            public String toString() {
                return "DataList{" +
                        "logId='" + logId + '\'' +
                        ", id='" + id + '\'' +
                        ", isDeleted=" + isDeleted +
                        ", addTime='" + addTime + '\'' +
                        ", addAccountId='" + addAccountId + '\'' +
                        ", addAccountName='" + addAccountName + '\'' +
                        ", editTime='" + editTime + '\'' +
                        ", editAccountId='" + editAccountId + '\'' +
                        ", editAccountName='" + editAccountName + '\'' +
                        ", organizationId='" + organizationId + '\'' +
                        ", organizationCode='" + organizationCode + '\'' +
                        ", organizationName='" + organizationName + '\'' +
                        ", topOrganizationId='" + topOrganizationId + '\'' +
                        ", topOrganizationCode='" + topOrganizationCode + '\'' +
                        ", topOrganizationName='" + topOrganizationName + '\'' +
                        ", parentId='" + parentId + '\'' +
                        ", contacts='" + contacts + '\'' +
                        ", contactsPhone='" + contactsPhone + '\'' +
                        ", accountSource=" + accountSource +
                        ", auditState=" + auditState +
                        ", state=" + state +
                        ", auditAccountId='" + auditAccountId + '\'' +
                        ", auditAccountName='" + auditAccountName + '\'' +
                        ", auditTime='" + auditTime + '\'' +
                        ", auditReason='" + auditReason + '\'' +
                        '}';
            }
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "A{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
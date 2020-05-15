package com.kibaki.insurance.Model;

public class PolicyModel {

    String policy_id,policy_name,policy_premium_frequrency,policy_payment_mode, policy_sum_assured,policy_total_premium;
    String policy_term,policy_maturity_date,policy_subcriber_loginID;

    public PolicyModel() {
    }

    public PolicyModel(String policy_id, String policy_name, String policy_premium_frequrency, String policy_payment_mode, String policy_sum_assured, String policy_total_premium, String policy_term, String policy_maturity_date, String policy_subcriber_loginID) {
        this.policy_id = policy_id;
        this.policy_name = policy_name;
        this.policy_premium_frequrency = policy_premium_frequrency;
        this.policy_payment_mode = policy_payment_mode;
        this.policy_sum_assured = policy_sum_assured;
        this.policy_total_premium = policy_total_premium;
        this.policy_term = policy_term;
        this.policy_maturity_date = policy_maturity_date;
        this.policy_subcriber_loginID = policy_subcriber_loginID;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getPolicy_premium_frequrency() {
        return policy_premium_frequrency;
    }

    public void setPolicy_premium_frequrency(String policy_premium_frequrency) {
        this.policy_premium_frequrency = policy_premium_frequrency;
    }

    public String getPolicy_payment_mode() {
        return policy_payment_mode;
    }

    public void setPolicy_payment_mode(String policy_payment_mode) {
        this.policy_payment_mode = policy_payment_mode;
    }

    public String getPolicy_sum_assured() {
        return policy_sum_assured;
    }

    public void setPolicy_sum_assured(String policy_sum_assured) {
        this.policy_sum_assured = policy_sum_assured;
    }

    public String getPolicy_total_premium() {
        return policy_total_premium;
    }

    public void setPolicy_total_premium(String policy_total_premium) {
        this.policy_total_premium = policy_total_premium;
    }

    public String getPolicy_term() {
        return policy_term;
    }

    public void setPolicy_term(String policy_term) {
        this.policy_term = policy_term;
    }

    public String getPolicy_maturity_date() {
        return policy_maturity_date;
    }

    public void setPolicy_maturity_date(String policy_maturity_date) {
        this.policy_maturity_date = policy_maturity_date;
    }

    public String getPolicy_subcriber_loginID() {
        return policy_subcriber_loginID;
    }

    public void setPolicy_subcriber_loginID(String policy_subcriber_loginID) {
        this.policy_subcriber_loginID = policy_subcriber_loginID;
    }
}

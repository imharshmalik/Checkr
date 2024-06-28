import React from "react";
import SignInTemplate from "../../templates/SignIn";
import { ForgotPassword } from "../../molecules/ForgotPassword";
import PrivacyPolicyImage from "../../../assets/images/Privacy policy-pana.svg";

const ForgotPasswordPage = () => {
  return (
    <SignInTemplate image={PrivacyPolicyImage}>
      <ForgotPassword />
    </SignInTemplate>
  );
};

export default ForgotPasswordPage;

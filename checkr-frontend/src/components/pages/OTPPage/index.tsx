import SignInTemplate from "../../templates/SignIn";
import Privacy_Policy from "../../../assets/images/Privacy policy-pana.svg";
import OTPInputSection from "../../molecules/OTPSection";

const OTPPage = () => {
  return (
    <SignInTemplate image={Privacy_Policy}>
      <OTPInputSection />
    </SignInTemplate>
  );
};

export default OTPPage;

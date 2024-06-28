import Privacy_Policy from "../../../assets/images/Privacy policy-pana.svg";
import { SignUp } from "../../organisms/SignUp";
import SignInTemplate from "../../templates/SignIn";

export default function SignUpPage() {
  return (
    <SignInTemplate image={Privacy_Policy}>
      <SignUp />
    </SignInTemplate>
  );
}

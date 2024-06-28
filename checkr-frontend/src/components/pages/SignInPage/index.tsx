import Privacy_Policy from "../../../assets/images/Privacy policy-pana.svg";
import { Signin } from "../../organisms/SignIn";
import SignInTemplate from "../../templates/SignIn";

export default function SignInPage() {
  return (
    <SignInTemplate image={Privacy_Policy}>
      <Signin />
    </SignInTemplate>
  );
}

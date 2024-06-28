import { render } from "@testing-library/react";
import SuccessModalBox from ".";
import Gif from "../../../assets/images/CheckGif.gif";
import { GIF_HEADING } from "../../../constants";

describe("SuccessModalBox", () => {
  it("should render the modal when `isOpen` is true", async () => {
    const { getByText } = render(
      <SuccessModalBox isOpen heading={GIF_HEADING} gifSrc={Gif} />
    );
    expect(
      // eslint-disable-next-line testing-library/prefer-screen-queries
      getByText("Download link was sent to your email!!")
    ).toBeInTheDocument();
  });

  it("should not render the modal when `isOpen` is false", async () => {
    const { queryByText } = render(
      <SuccessModalBox isOpen={false} heading={GIF_HEADING} gifSrc={Gif} />
    );
    // eslint-disable-next-line testing-library/prefer-screen-queries
    expect(queryByText("Download link was sent to your email!!")).toBeNull();
  });
});

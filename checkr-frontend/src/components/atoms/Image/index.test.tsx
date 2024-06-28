import { render, screen } from "@testing-library/react";
import CustomImage from ".";

const PolicyImg = "../../../assets/images/Privacy policy-pana (1) 2.svg";
const imageAltText = "is-image-present";

describe("Testcases for the Image atom", () => {
  // eslint-disable-next-line jest/valid-title
  test("test is Image renders correctly or not", () => {
    render(<CustomImage src={PolicyImg} alt={imageAltText} />);
    const img = screen.getByRole("img");
    expect(img).toBeInTheDocument();
    expect(img).toHaveAttribute("src", PolicyImg);
    expect(img).toHaveAttribute("alt", imageAltText);
  });
});

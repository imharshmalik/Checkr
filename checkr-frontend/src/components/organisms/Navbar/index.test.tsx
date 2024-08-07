import { screen, render, fireEvent } from "@testing-library/react";
import { Navbar } from ".";
import { NAV_LISTS } from "../../../constants";
import { NavFooterProps } from "../../molecules/NavFooter";
import ProfilePicture from "../../../assets/images/Avatar.svg";
import IconPicture from "../../../assets/images/Vector.svg";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider, createTheme } from "@mui/material/styles";

const theme = createTheme({
  breakpoints: {
    values: {
      xs: 0,
      sm: 600,
      md: 960,
      lg: 1280,
      xl: 1920,
    },
  },
});

describe("Navbar testcases", () => {
  const navFooterProps: NavFooterProps = {
    avatarSrc: ProfilePicture,
    avatarAlt: "Avatar",
    avatarSx: { width: "40px", height: "40px" },
    userNameVariant: "body1",
    userName: "John Doe",
    userNameSx: { fontWeight: "bold" },
    emailVariant: "caption2",
    email: "johndoe@example.com",
    emailSx: { color: "gray" },
    iconSrc: IconPicture,
    iconAlt: "Icon",
    iconStyle: { width: "20px", height: "20px" },
  };
  it("desktop view", () => {
    window.matchMedia = jest.fn().mockImplementation((query) => ({
      matches: query === "(max-width: 999px",
      media: "",
      onchange: null,
      addListener: jest.fn(),
      removeListener: jest.fn(),
    }));
    render(
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <Navbar
            navFooterProps={navFooterProps}
            navLists={NAV_LISTS}
            activeTab="Candidates"
          />
        </ThemeProvider>
      </BrowserRouter>
    );
    const desktopView = screen.getByTestId("navbar");
    expect(desktopView).toBeInTheDocument();
  });
  it("mobile view", () => {
    window.matchMedia = jest.fn().mockImplementation((query) => ({
      matches: query !== "(max-width: 999px",
      media: "",
      onchange: null,
      addListener: jest.fn(),
      removeListener: jest.fn(),
    }));
    render(
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <Navbar
            navFooterProps={navFooterProps}
            navLists={NAV_LISTS}
            activeTab="Candidates"
          />
        </ThemeProvider>
      </BrowserRouter>
    );
    const mobileView = screen.getByTestId("mobile-view");
    expect(mobileView).toBeInTheDocument();
    const toggleIcon = screen.getByTestId("toggle-icon");
    fireEvent.click(toggleIcon);
  });
  // eslint-disable-next-line jest/no-identical-title
  it("desktop view", () => {
    window.matchMedia = jest.fn().mockImplementation((query) => ({
      matches: query === "(max-width: 999px",
      media: "",
      onchange: null,
      addListener: jest.fn(),
      removeListener: jest.fn(),
    }));
    render(
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <Navbar
            navFooterProps={navFooterProps}
            navLists={NAV_LISTS}
            activeTab="Candidates"
          />
        </ThemeProvider>
      </BrowserRouter>
    );
    const desktopView = screen.getByTestId("desktop-view");
    expect(desktopView).toBeInTheDocument();
  });
  // it("test_render_navbar_correctly", () => {
  //   render(
  //     <BrowserRouter>
  //       <ThemeProvider theme={theme}>
  //         <Navbar
  //           navFooterProps={navFooterProps}
  //           navLists={NAV_LISTS}
  //           activeTab="Candidates"
  //         />
  //       </ThemeProvider>
  //     </BrowserRouter>
  //   );
  //   const navbarElement = screen.getByTestId("navbar");
  //   expect(navbarElement).toBeInTheDocument();
  //   expect(screen.getByAltText("logo")).toBeInTheDocument();
  // });
  // it("displays navlists correctly", () => {
  //   render(
  //     <BrowserRouter>
  //       <ThemeProvider theme={theme}>
  //         <Navbar
  //           navFooterProps={navFooterProps}
  //           navLists={NAV_LISTS}
  //           activeTab="Candidates"
  //         />
  //       </ThemeProvider>
  //     </BrowserRouter>
  //   );
  //   expect(screen.getByText("Home")).toBeInTheDocument();
  //   expect(screen.getByText("Candidates")).toBeInTheDocument();
  // });
  // it("test display navfooter", () => {
  //   render(
  //     <BrowserRouter>
  //       <ThemeProvider theme={theme}>
  //         <Navbar
  //           navFooterProps={navFooterProps}
  //           navLists={NAV_LISTS}
  //           activeTab="Candidates"
  //         />
  //       </ThemeProvider>
  //     </BrowserRouter>
  //   );
  //   expect(screen.getByTestId("nav-footer")).toBeInTheDocument();
  // });
  // it("clicking on each nav item", () => {
  //   render(
  //     <BrowserRouter>
  //       <ThemeProvider theme={theme}>
  //         <Navbar
  //           navFooterProps={navFooterProps}
  //           navLists={NAV_LISTS}
  //           activeTab="Candidates"
  //         />
  //       </ThemeProvider>
  //     </BrowserRouter>
  //   );
  //   const candidateNavItem = screen.getByText("Candidates");
  //   expect(candidateNavItem).toBeInTheDocument();
  //   fireEvent.click(candidateNavItem);
  //   const adverseActionNavItem = screen.getByText("Adverse Actions");
  //   expect(adverseActionNavItem).toBeInTheDocument();
  //   fireEvent.click(adverseActionNavItem);
  // });
  // it("when handleclick fn is not sent as prop", () => {
  //   render(
  //     <BrowserRouter>
  //       <ThemeProvider theme={theme}>
  //         <Navbar
  //           navFooterProps={navFooterProps}
  //           navLists={NAV_LISTS}
  //           activeTab="Candidates"
  //         />
  //       </ThemeProvider>
  //     </BrowserRouter>
  //   );
  //   const candidateNavItem = screen.getByText("Candidates");
  //   expect(candidateNavItem).toBeInTheDocument();
  //   fireEvent.click(candidateNavItem);
  // });
});

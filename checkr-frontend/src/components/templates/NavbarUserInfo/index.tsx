import { Grid, styled, useMediaQuery } from "@mui/material";
import React from "react";
import theme from "../../../theme/theme";
import "./styles.css";

interface NavbarUserInfoProps {
  sidebar?: React.ReactNode;
  header?: React.ReactNode;
  content?: React.ReactNode;
}
const StyledOutline = styled(Grid)(({ ismobile }: { ismobile: boolean }) => ({
  margin: "0px",
  width: "100%",
  height: "98vh",
  padding: "24px",
  display: "grid",
  gap: "20px",
  gridTemplateColumns: `${ismobile ? "minmax(5px, max-content) 1fr" : "minmax(236px, max-content) 1fr"}`,
  gridTemplateRows: "minmax(56px, max-content) 1fr",
  backgroundColor: theme.palette.primaryColor[100],
}));
const StyledNavBar = styled(Grid)({
  gridArea: "1/1/3/2",
});

const StyledHeader = styled(Grid)({
  gridArea: "1/2/2/3",
  marginTop: "20px",
});
const StyledMainContent = styled(Grid)({
  gridArea: "2/2/3/3",
  overflow: "auto",
  scrollbarWidth: "thin",
  scrollbarColor: "transparent transparent",
  "&::-webkit-scrollbar": {
    width: "0px",
  },
  "&::-webkit-scrollbar-thumb": {
    backgroundColor: "transparent",
  },
});
const NavbarUserInfoTemplate = ({
  sidebar,
  header,
  content,
}: NavbarUserInfoProps) => {
  const ismobile = useMediaQuery(theme.breakpoints.down("md"));
  return (
    <StyledOutline ismobile={ismobile} data-testid="outline">
      <StyledNavBar className={!sidebar ? "applyBorder" : ""}>
        {sidebar}
      </StyledNavBar>
      <StyledHeader className={!header ? "applyBorder" : ""}>
        {header}
      </StyledHeader>
      <StyledMainContent className={!content ? "applyBorder" : ""}>
        {content}
      </StyledMainContent>
    </StyledOutline>
  );
};
export default NavbarUserInfoTemplate;

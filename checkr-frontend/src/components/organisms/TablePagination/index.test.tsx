import { render, screen, fireEvent } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import theme from "../../../theme/theme";
import PaginatedItems from ".";
import { TABLE_DATA } from "../../../constants";

describe("PaginatedItems", () => {
  const tableData = TABLE_DATA;
  const mockColumns = [
    {
      id: 3,
      key: "pre_notice_date",
      name: "PRE NOTICE DATE",
      width: "15.66vw",
    },
  ];

  it("renders the component with correct data", () => {
    render(
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <PaginatedItems
            tableData={tableData}
            columns={mockColumns}
            showCandidatesFilter={false}
          />
        </ThemeProvider>
      </BrowserRouter>
    );
  });

  it("searching something in search bar", () => {
    render(
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <PaginatedItems
            tableData={tableData}
            columns={mockColumns}
            showCandidatesFilter={false}
          />
        </ThemeProvider>
      </BrowserRouter>
    );
    const inputfield = screen.getByPlaceholderText("Search any candidate");
    fireEvent.change(inputfield, { target: { value: "jo" } });
  });
});

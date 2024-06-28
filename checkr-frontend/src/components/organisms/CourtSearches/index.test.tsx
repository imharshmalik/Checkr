/* eslint-disable testing-library/prefer-screen-queries */
import { render, screen } from "@testing-library/react";
import CourtSearches from ".";
import { ThemeProvider } from "@emotion/react";
import theme from "../../../theme/theme";

describe("CourtSearches", () => {
  const rowsData = [
    { id: 1, search: "Search 1", status: "Status 1", date: "Date 1" },
    { id: 2, search: "Search 2", status: "Status 2", date: "Date 2" },
  ];

  it("renders the table with the provided row data", () => {
    const { getAllByRole, getByText } = render(
      <ThemeProvider theme={theme}>
        <CourtSearches rowsData={rowsData} />
      </ThemeProvider>
    );
    const table = getAllByRole("table");
    expect(table.length).toBe(1);
    const tableHeaders = getAllByRole("columnheader");
    expect(tableHeaders.length).toBe(5);
    const tableRows = getAllByRole("row");
    expect(tableRows.length).toBe(rowsData.length);
    rowsData.forEach((row, index) => {
      const searchCell = getByText(new RegExp(row.search, "i"));
      const statusCell = getByText(new RegExp(row.status, "i"));

      expect(searchCell).toBeInTheDocument();
      expect(statusCell).toBeInTheDocument();
    });
  });

  it("renders an empty table when no row data is provided", () => {
    const { getAllByRole } = render(
      <ThemeProvider theme={theme}>
        <CourtSearches />
      </ThemeProvider>
    );

    const tableRows = getAllByRole("row");
    expect(tableRows.length).toBe(2);
  });
});

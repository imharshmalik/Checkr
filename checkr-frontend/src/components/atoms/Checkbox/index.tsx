import React from "react";
import Checkbox, { CheckboxProps } from "@mui/material/Checkbox";
import { styled } from "@mui/material/styles";

import { useTheme } from "@mui/material/styles";

const StyledCheckbox = styled(Checkbox)<CheckboxProps>(() => {
  const theme = useTheme();

  return {
    "& .MuiSvgIcon-root": {
      fontSize: 18,
    },
    "&.MuiCheckbox-root": {
      padding: 0,
      color: `${theme.palette.greyColor.stroke || "#cccccc"}`,
    },
    "&.Mui-checked": {
      color: theme.palette.primaryColor[500],
    },
  };
});

export const MuiCheckbox = (props: CheckboxProps) => {
  return (
    <div>
      <StyledCheckbox {...props} />
    </div>
  );
};

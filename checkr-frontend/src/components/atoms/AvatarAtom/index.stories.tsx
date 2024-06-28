import { Meta, StoryFn } from "@storybook/react";
import AvatarAtom from "./index";

export default {
  title: "atoms/AvatarAtom",
  component: AvatarAtom,
} as Meta;

const Template: StoryFn<typeof AvatarAtom> = (args) => <AvatarAtom {...args} />;

export const Default = Template.bind({});
Default.args = {
  src: "https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
  alt: "Avatar",
};

import React from "react";

//pages
import Groups from "../views/dashboard/app/groups";
import GroupDetail from "../views/dashboard/app/group-detail";
import FriendList from "../views/dashboard/app/friend-list";
import ProfileImages from "../views/dashboard/app/profile-images";
import ProfileVideos from "../views/dashboard/app/profile-videos";
import ProfileEvents from "../views/dashboard/app/profile-events";
import EventDetail from "../views/dashboard/app/event-detail";
import Calendar from "../views/dashboard/app/calendar";
import Birthday from "../views/dashboard/app/birthday";
import Weather from "../views/dashboard/app/weather";
import Music from "../views/dashboard/app/music";
import CatergoryGrid from "../views/dashboard/store/store-category-grid";
import CatergoryList from "../views/dashboard/store/store-category-list";
import StoreDetail from "../views/dashboard/store/store-detail";
import StoreCheckout from "../views/dashboard/store/store-checkout";

import DanceNewsfeed from "../views/dashboard/app/dance-newsfeed";
import DanceEvents from "../views/dashboard/app/dance-events";
import DanceTickets from "../views/dashboard/app/dance-tickets";

//market and profile pages
import Market1 from "../views/dashboard/market-place/market1";
import Market2 from "../views/dashboard/market-place/market2";
import Profile1 from "../views/dashboard/profiles/profile1";
import Profile2 from "../views/dashboard/profiles/profile2";
import Profile3 from "../views/dashboard/profiles/profile3";
import DanceEventDetail from "../views/dashboard/app/dance-event-detail";
import DanceBuyTickets from "../views/dashboard/app/dance-buy-tickets";
import DanceImageGallery from "../components/image-gallery/image-gallery";
import DanceTicketManager from "../views/dashboard/app/dance-ticket-manager";
import DanceAdmin from "../views/dashboard/app/dance-admin";
import PrivateRoute from "./private-route";

export const Layout1Router = [
  {
    path: "dashboards/app/dance-newsfeed",
    element: <DanceNewsfeed />,
  },
  {
    path: "dashboards/app/dance-events",
    element: <DanceEvents />,
  },
  {
    path: "dashboards/app/dance-tickets",
    element: <DanceTickets />,
  },
  {
    path: "dashboards/app/dance-ticket-manager/:id",
    element: <PrivateRoute><DanceTicketManager /></PrivateRoute>,
  },
  {
    path: "dashboards/app/groups",
    element: <Groups />,
  },
  {
    path: "dashboards/app/group-detail",
    element: <GroupDetail />,
  },
  {
    path: "dashboards/app/friend-list",
    element: <FriendList />,
  },
  {
    path: "dashboards/app/profile-images",
    element: <ProfileImages />,
  },
  {
    path: "dashboards/app/profile-videos",
    element: <ProfileVideos />,
  },
  {
    path: "dashboards/app/profile-events",
    element: <ProfileEvents />,
  },
  {
    path: "dashboards/app/dance-event-detail/:id",
    element: <DanceEventDetail />,
  },
  {
    path: "dashboards/app/dance-admin/:id",
    element: <PrivateRoute><DanceAdmin /></PrivateRoute>,
  },
  {
    path: "dashboards/app/dance-event-detail/:id/dance-buy-ticket",
    element: <DanceBuyTickets />,
  },
  {
    path: "dashboards/app/event-detail",
    element: <EventDetail />,
  },
  {
    path: "dashboards/app/calendar",
    element: <Calendar />,
  },
  {
    path: "dashboards/app/birthday",
    element: <Birthday />,
  },
  {
    path: "dashboards/app/weather",
    element: <Weather />,
  },
  {
    path: "dashboards/app/music",
    element: <Music />,
  },
  {
    path: "dashboards/store/store-category-grid",
    element: <CatergoryGrid />,
  },
  {
    path: "dashboards/store/store-category-list",
    element: <CatergoryList />,
  },
  {
    path: "dashboards/store/store-detail",
    element: <StoreDetail />,
  },
  {
    path: "dashboards/store/store-checkout",
    element: <StoreCheckout />,
  },
  {
    path: "dashboards/market-place/market1",
    element: <Market1 />,
  },
  {
    path: "dashboards/market-place/market2",
    element: <Market2 />,
  },
  {
    path: "dashboards/profiles/profile1",
    element: <Profile1 />,
  },
  {
    path: "dashboards/profiles/profile2",
    element: <Profile2 />,
  },
  {
    path: "dashboards/profiles/profile3",
    element: <Profile3 />,
  },
];

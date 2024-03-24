//router
// import IndexRouters from "./router/index"

//scss
import "./assets/scss/socialv.scss"
import "./assets/scss/customizer.scss"

// Redux Selector / Action
import { useDispatch } from 'react-redux';

// import state selectors
import { setSetting } from './store/setting/actions'
import React from "react";
import {ReactKeycloakProvider} from "@react-keycloak/web";
import keycloak from "./keycloak";
import {ToastContainer} from "react-toastify";

function App(props) {
  const dispatch = useDispatch()
  dispatch(setSetting())

  return (
    <div className="App">
      <ToastContainer
          position="bottom-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="light"
      />
      <ReactKeycloakProvider authClient={keycloak}>
        {/* <IndexRouters /> */}
        {props.children}
      </ReactKeycloakProvider>
    </div>
  );
}

export default App;

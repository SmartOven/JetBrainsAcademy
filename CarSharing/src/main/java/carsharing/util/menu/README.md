# Menu architecture

MainMenu  |--> ManagerMenu          |--> CompanyChoosingMenu |--> CompanyActionsMenu |--> CreateCarMenu
          |                         |                        |                       |--> back to ManagerMenu
          |                         |                        |--> back to ManagerMenu
          |                         |--> CreateCompanyMenu
          |                         |--> back to MainMenu
          |
          |--> ChoosingCustomerMenu |--> CustomerMenu        |--> ChooseCompanyMenu |--> ChooseCarMenu
          |                         |                        |--> back to MainMenu
          |                         |--> back to MainMenu
          |--> CreateCustomerMenu
          |--> exit
using EmployeePortal.Builder.IBuilder;
using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Builder.Director
{
    public class ConfigurationBuilder
    {
        public void BuildSystem(ISystemBuilder systemBuilder,IFormCollection valueCollection) 
        {
            systemBuilder.SetHDD(valueCollection["HDD"].ToString())
            .SetKEYBOARD(valueCollection["KEYBOARD"].ToString())
            .SetMOUSE(valueCollection["MOUSE"].ToString())
            .SetRAM(valueCollection["RAM"].ToString())
            .SetTOUCHSCREEN(valueCollection["TOUCHSCREEN"].ToString());
        }
    }
}

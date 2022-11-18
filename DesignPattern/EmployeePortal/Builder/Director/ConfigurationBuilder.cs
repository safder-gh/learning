using EmployeePortal.Builder.IBuilder;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Builder.Director
{
    public class ConfigurationBuilder
    {
        public void BuildSystem(ISystemBuilder systemBuilder,NameValueCollection valueCollection) 
        {
            systemBuilder.SetHDD(valueCollection["HDD"]);
            systemBuilder.SetKEYBOARD(valueCollection["KEYBOARD"]);
            systemBuilder.SetMOUSE(valueCollection["MOUSE"]);
            systemBuilder.SetRAM(valueCollection["RAM"]);
            systemBuilder.SetTOUCHSCREEN(valueCollection["TOUCHSCREEN"]);
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace viewdata_viewbag_tempdata.testing
{
    public class AbsLoggerFactory
    {
        public static ILogger GetLogger(string type = "")
        {
            ILogger logger = new TextLogger();
            if (type == "db")
            {
                logger = new DBLogger();
            }
            else if (type == "api")
            {
                logger = new APILogger();
            }
            return logger;
        }
    }
}

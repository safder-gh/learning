using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace viewdata_viewbag_tempdata.testing
{
    public class TextLogger : ILogger
    {
        public string readLog()
        {
            return "log from Text File";
        }

        public void writeLog(string log)
        {
            //write in text file
        }
    }
    public class DBLogger : ILogger
    {
        public string readLog()
        {
            return "log from DB";
        }

        public void writeLog(string log)
        {
            //write in DB
        }
    }
    public class APILogger : ILogger
    {
        public string readLog()
        {
            return "log from api";
        }

        public void writeLog(string log)
        {
            //write to api
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace viewdata_viewbag_tempdata.testing
{
    public interface ILogger
    {
        public void writeLog(string log);
        public string readLog();
    }
}

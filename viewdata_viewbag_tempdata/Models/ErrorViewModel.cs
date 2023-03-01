using System;
using viewdata_viewbag_tempdata.testing;

namespace viewdata_viewbag_tempdata.Models
{
    public class ErrorViewModel: AbsClass
    {
        public string RequestId { get; set; }

        public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);

        public override void absMethod()
        {
            int num = 100;
            Int16 myInt =(Int16)num;
        }
    }
}

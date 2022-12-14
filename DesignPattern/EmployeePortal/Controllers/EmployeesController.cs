using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using EmployeePortal.DAL;
using EmployeePortal.Models;
using EmployeePortal.SimpleFactory.Managers;
using EmployeePortal.SimpleFactory.Factory;
using EmployeePortal.FactoryMethod;
using EmployeePortal.AbstractFactory;
using EmployeePortal.ViewModel;
using Microsoft.AspNetCore.Http;
using EmployeePortal.Builder.IBuilder;
using EmployeePortal.Builder.Director;
using EmployeePortal.Builder.ConcreteBuilder;
using EmployeePortal.Builder.Product;

namespace EmployeePortal.Controllers
{
    public class EmployeesController : Controller
    {
        private readonly DesignPatternContext _context;

        public EmployeesController(DesignPatternContext context)
        {
            _context = context;
        }

        // GET: Employees
        public async Task<IActionResult> Index()
        {
            return View(await _context.Employees.ToListAsync());
        }
        [HttpGet]
        public IActionResult BuildSystem(Guid? id)
        {
            Employee employee = _context.Employees.Find(id);
            if (employee.ComputerDetails.Contains("Laptop"))
            {
                return View("LaptopSystem", (Guid)id);
            }
            else
            {
                return View("DesktopSystem", (Guid)id);
            }
        }
        [HttpPost]
        public IActionResult DesktopSystem(IFormCollection formCollection)
        {
            Employee employee = _context.Employees.Find(new Guid(formCollection["id"]));
            ISystemBuilder systemBuilder = new DesktopBuilder();
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.BuildSystem(systemBuilder, formCollection);
            ComputerSystem computerSystem = systemBuilder.GetSystem();
            employee.SystemConfigurationDetails = String.Format("RAM {0} , HDD {1} , Keyboard {2} , Mouse {3} , Touchscreen {4} ",computerSystem.RAM,computerSystem.HDD,computerSystem.KEYBOARD,computerSystem.MOUSE,computerSystem.TOUCHSCREEN);
            _context.Entry(employee).State = EntityState.Modified;
            _context.SaveChanges();
            return RedirectToAction("Index");
        }
        [HttpPost]
        public IActionResult LaptopSystem(IFormCollection formCollection)
        {
            Employee employee = _context.Employees.Find(new Guid(formCollection["id"]));
            ISystemBuilder systemBuilder = new LaptopBuilder();
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.BuildSystem(systemBuilder, formCollection);
            ComputerSystem computerSystem = systemBuilder.GetSystem();
            employee.SystemConfigurationDetails = String.Format("RAM {0} , HDD {1} , Keyboard {2} , Mouse {3} , Touchscreen {4} ", computerSystem.RAM, computerSystem.HDD, computerSystem.KEYBOARD, computerSystem.MOUSE, computerSystem.TOUCHSCREEN);
            _context.Entry(employee).State = EntityState.Modified;
            _context.SaveChanges();
            return RedirectToAction("Index");
        }
        [HttpPost]
        public IActionResult BuildSystem([Bind("Id", "RAM", "HDD", "TOUCHSCREEN", "KEYBOARD", "MOUSE")] SystemConfigurationDetails systemConfigurationDetails)
        {
            Employee employee = _context.Employees.Find(systemConfigurationDetails.Id);
            employee.SystemConfigurationDetails = systemConfigurationDetails.BuidSystem();
            _context.Entry(employee).State = EntityState.Modified;
            _context.SaveChanges();
            return RedirectToAction("Index");
        }
        // GET: Employees/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var employee = await _context.Employees
                .FirstOrDefaultAsync(m => m.Id == id);
            if (employee == null)
            {
                return NotFound();
            }

            return View(employee);
        }

        // GET: Employees/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Employees/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("FirstName,LastName,JD,EmployeeType,JobType,HourlyPay,Bonus,Id,Deleted")] Employee employee)
        {
            if (ModelState.IsValid)
            {
                employee.Id = Guid.NewGuid();
                //EmployeeFactoryManager employeeFactoryManager = new EmployeeFactoryManager();
                //IEmployeeManager employeeManager = employeeFactoryManager.GetManager(employee.EmployeeType);
                //employee.Bonus = employeeManager.GetBonus();
                //employee.HourlyPay = employeeManager.GetHourlyPay();
                BaseEmployeeFactory baseEmployeeFactory = new EmployeeManagerFactory().CreateFactory(employee);
                employee = baseEmployeeFactory.ApplySalary();
                IComputerFactory computerFactory = new EmployeeSystemFactory().Create(employee);
                EmployeeSystemManager employeeSystemManager = new EmployeeSystemManager(computerFactory);
                employee.ComputerDetails = employeeSystemManager.GetsystemDetails();
                _context.Add(employee);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(employee);
        }

        // GET: Employees/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var employee = await _context.Employees.FindAsync(id);
            if (employee == null)
            {
                return NotFound();
            }
            return View(employee);
        }

        // POST: Employees/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("FirstName,LastName,JD,EmployeeType,HourlyPay,Bonus,Id,Deleted")] Employee employee)
        {
            if (id != employee.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(employee);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!EmployeeExists(employee.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(employee);
        }

        // GET: Employees/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var employee = await _context.Employees
                .FirstOrDefaultAsync(m => m.Id == id);
            if (employee == null)
            {
                return NotFound();
            }

            return View(employee);
        }

        // POST: Employees/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            var employee = await _context.Employees.FindAsync(id);
            _context.Employees.Remove(employee);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool EmployeeExists(Guid id)
        {
            return _context.Employees.Any(e => e.Id == id);
        }
    }
}

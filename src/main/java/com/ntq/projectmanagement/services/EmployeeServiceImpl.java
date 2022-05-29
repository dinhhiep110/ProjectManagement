package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.dto.EmployeeDto;
import com.ntq.projectmanagement.entities.Employee;
import com.ntq.projectmanagement.entities.Language;
import com.ntq.projectmanagement.entities.Project;
import com.ntq.projectmanagement.entities.Projectemployee;
import com.ntq.projectmanagement.repository.EmployeeRepository;
import com.ntq.projectmanagement.repository.ProjectemployeeRepository;
import com.ntq.projectmanagement.settings.EmployeeFilterRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private SettingService settingService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectemployeeRepository projectemployeeRepository;

    @Override
    public long countAllEmployee() {
        return employeeRepository.count();
    }

    @Override
    public long countActiveEmployee(long id){
        return employeeRepository.countEmployeeByStatusId(id);
    }

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = modelMapper.map(employeeList,new TypeToken<List<EmployeeDto>>(){}.getType());
        return employeeDtos;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getAvailableEmployeeForProject(Project project) {
        int limit_employee = Integer.parseInt(settingService.getValueFromKey("Project_limit_employee").getValue());
        boolean isContainLanguage = false,isExistEmployee = false;
        List<Employee> result = new ArrayList<>();
        Set<Language> languageSet = project.getLanguages();
        Set<Projectemployee> employees = project.getProjectemployees();
        List<Employee> allEmployee = getActiveEmployee();
        for (Employee employee: allEmployee) {
            int count = projectemployeeRepository.countAllByEmployeeIdAndIsRejectedFalse(employee.getId());
            if(employee.getRole().getId() != 1 && employee.getRole().getId() != 2 && employee.getRole().getId() != 7){
                for(Language language : employee.getLanguages()){
                    if(languageSet.contains(language)){
                        isContainLanguage = true;
                        break;
                    }
                }
            }
            else{
                isContainLanguage = true;
            }
            for (Projectemployee projectemployee: employees) {
                if (Objects.equals(projectemployee.getEmployee().getId(), employee.getId())) {
                    isExistEmployee = true;
                    break;
                }
            }
            if(isContainLanguage && count < limit_employee && !isExistEmployee){
                result.add(employee);
            }
            isExistEmployee = false;
            isContainLanguage = false;
        }
        return result;
    }

    @Override
    public List<Employee> getActiveEmployee() {
        return employeeRepository.getAllByStatusId(3L);
    }

    @Override
    public void addUpdateEmployee(Employee employee) {
        employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Page<Employee> getEmployeePaginated(int pageNumber, EmployeeFilterRequest filterRequest) {
        Sort sort = Sort.by("updateAt").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1,filterRequest.getPageSize(),sort);
        return employeeRepository.filterEmployeeWithKeyWord(filterRequest,pageable);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        return optional.orElseGet(Employee::new);
    }

    @Override
    public EmployeeDto getEmployeesById(long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = optional.orElseGet(Employee::new);
        return modelMapper.map(employee,new EmployeeDto().getClass());
    }
}

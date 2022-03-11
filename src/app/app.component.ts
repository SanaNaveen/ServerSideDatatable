import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UserDataService } from './service/user_data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'ServerSideDataTableUI';

  searchForm !:  FormGroup ;

  page = 1;
  pageSize = 15;
  totalItems : any;
  userDataList : any[] =[];

  

  filter = '';
  constructor(private userDataService : UserDataService,private fb : FormBuilder) {}

  ngOnInit(): void {
     // this.getUserData();

      this.searchForm = this.fb.group({

        'fullName' :[null],
        'emailId' :[null],
        'country' :[null]

      });
  }




 

  
}

import { Component, OnInit } from '@angular/core';
import { UserDataService } from './service/user_data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'ServerSideDataTableUI';

  page = 1;
  pageSize = 5;
  totalItems : any;
  userDataList : any[] =[];



  filter = '';
  constructor(private userDataService : UserDataService) {}

  ngOnInit(): void {
      this.getUserData();
  }


  getUserData(){

    let req= {
      "pageNo":this.page,
	    "pageSize":this.pageSize,
	    "sortBy":"fullName"
    }

    this.userDataService.getUserData(req).subscribe((data) => {

      this.userDataList = data.responseList.content;

      this.totalItems = data.responseList.totalElements-1;
    });

  }

  nextPage(page : any){

    let req= {
      "pageNo":page,
	    "pageSize":this.pageSize,
	    "sortBy":"fullName"
    }

    this.userDataService.getUserData(req).subscribe((data) => {

      this.userDataList = data.responseList.content;

      this.totalItems = data.responseList.totalElements-1;
    });

  }

 
}

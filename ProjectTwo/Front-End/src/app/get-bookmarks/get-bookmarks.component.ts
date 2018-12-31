import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { HelperServiceService } from '../Services/helper-service.service';

@Component({
  selector: 'app-get-bookmarks',
  templateUrl: './get-bookmarks.component.html',
  styleUrls: ['./get-bookmarks.component.css']
})
export class GetBookmarksComponent implements OnInit {
  session: any;
  display: any;
  bookmarksUrl = 'http://ec2-54-210-42-186.compute-1.amazonaws.com:8080/Pipeline/bookmark/view';
  result: any;
  gotBookmarks = false;

  constructor(private http: HttpClient, private helpMe: HelperServiceService){ }

  ngOnInit() {
  }
  getSession(){
    this.session = this.helpMe.getSession();
    this.display = this.session.currentUser;
  }
  getBookmarks(){
    console.log(this.helpMe.getSession());
    console.log("Bam, Bookmarks")
    this.getSession();
    this.bookmarkService().subscribe(result => {
      this.result = result;
      console.log(this.result) 
      this.gotBookmarks = true;
    })
  }
  
  bookmarkService(){
    
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    }
    return this.http.post<any[]>(this.bookmarksUrl, JSON.stringify(this.session), httpOptions).pipe(map(data => data));
  }

}

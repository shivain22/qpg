import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { CourseMasterDetailComponent } from 'app/entities/course-master/course-master-detail.component';
import { CourseMaster } from 'app/shared/model/course-master.model';

describe('Component Tests', () => {
  describe('CourseMaster Management Detail Component', () => {
    let comp: CourseMasterDetailComponent;
    let fixture: ComponentFixture<CourseMasterDetailComponent>;
    const route = ({ data: of({ courseMaster: new CourseMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [CourseMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CourseMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CourseMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load courseMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.courseMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

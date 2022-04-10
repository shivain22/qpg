import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubjectMasterDetailComponent } from 'app/entities/subject-master/subject-master-detail.component';
import { SubjectMaster } from 'app/shared/model/subject-master.model';

describe('Component Tests', () => {
  describe('SubjectMaster Management Detail Component', () => {
    let comp: SubjectMasterDetailComponent;
    let fixture: ComponentFixture<SubjectMasterDetailComponent>;
    const route = ({ data: of({ subjectMaster: new SubjectMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubjectMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SubjectMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubjectMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subjectMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subjectMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

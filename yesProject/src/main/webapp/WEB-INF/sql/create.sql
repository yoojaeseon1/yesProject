-- create table

create table branchAccept
(
  licenceNum varchar(20) not null
    primary key,
  branchName varchar(20) not null,
  address varchar(20) not null,
  openTime varchar(20) not null,
  menu varchar(20) not null,
  image varchar(20) not null,
  phoneNum varchar(20) not null,
  cartegory varchar(20) not null
) DEFAULT CHARSET=utf8mb4;
;

create table branchCounsel
(
  bCounselIndex int auto_increment
    primary key,
  title varchar(30) not null,
  content varchar(100) not null,
  writer varchar(20) not null,
  calendar date not null,
  comment varchar(100) null,
  questionSelect varchar(100) not null
) DEFAULT CHARSET=utf8mb4;
;

create table branchInfo
(
  id varchar(20) not null
    primary key,
  branchname varchar(20) not null,
  opTime varchar(20) not null,
  breakTime varchar(20) not null,
  opDate varchar(20) not null,
  phoneNum varchar(20) not null,
  score decimal(2,1) null,
  state varchar(20) not null,
  category varchar(20) not null,
  branchExplain varchar(40) not null,
  maxTable int not null,
  tableState int not null,
  acceptState varchar(10) not null,
  waitingNum int not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;


create table branchAddress
(
  id varchar(20) not null
    primary key,
  zoneCode varchar(10) not null,
  roadAddress varchar(50) not null,
  jibunAddress varchar(30) not null,
  detailAddress varchar(30) not null,
  sido varchar(20) not null,
  sigungu varchar(20) not null,
  bname1 varchar(20) null,
  bname2 varchar(20) not null,
  latlngx varchar(20) not null,
  latlngy varchar(20) not null,
  constraint fk_id
  foreign key (id) references branchInfo (id)
    on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;



create table branchImage
(
  id varchar(20) not null
    primary key,
  markerImage varchar(40) not null,
  mainImage varchar(40) not null,
  image1 varchar(40) not null,
  image2 varchar(40) null,
  image3 varchar(40) null,
  image4 varchar(40) null,
  image5 varchar(40) null,
  image6 varchar(40) null,
  image7 varchar(40) null,
  image8 varchar(40) null,
  constraint fk_img_id
  foreign key (id) references branchInfo (id)
    on delete cascade
) DEFAULT CHARSET=utf8mb4;
;

create table branchMenu
(
  id varchar(20) not null,
  menu varchar(20) not null,
  price varchar(20) not null,
  mainMenu varchar(20) not null,
  constraint fk_me_id
  foreign key (id) references branchInfo (id)
    on delete cascade
) DEFAULT CHARSET=utf8mb4;
;

create table cCounselImages
(
  cCounselImgIndex int not null,
  imageName varchar(100) null
) DEFAULT CHARSET=utf8mb4;
;

create table clientCounsel
(
  cCounselIndex int auto_increment
    primary key,
  branchID varchar(20) not null,
  clientID varchar(45) not null,
  title varchar(45) not null,
  content varchar(100) not null,
  writer varchar(45) not null,
  calendar datetime not null,
  comment varchar(100) null
) DEFAULT CHARSET=utf8mb4;
;

create table guest
(
  sabun int auto_increment
    primary key,
  name varchar(45) null,
  nalja date null,
  pay int null
) DEFAULT CHARSET=utf8mb4;
;

create table noticeBoard
(
  noticeIndex int auto_increment
    primary key,
  title varchar(20) not null,
  content varchar(100) not null,
  writer varchar(20) not null,
  calendar date null
) DEFAULT CHARSET=utf8mb4;
;

create table noticeImages
(
  noticeImgIndex int not null,
  imageName varchar(100) null
) DEFAULT CHARSET=utf8mb4;
;

create table reserveList
(
  reserveIndex int not null primary key auto_increment,
  branchID varchar(20) not null,
  clientID varchar(20) not null,
  reserveTime varchar(20) not null,
  checkTime datetime not null,
  numPerson int not null,
  request varchar(100) not null,
  useState varchar(10) not null
) DEFAULT CHARSET=utf8mb4;
;

create table reviewBoard
(
  branchID varchar(20) not null,
  clientID varchar(20) not null,
  reviewIndex int auto_increment
    primary key,
  title varchar(20) not null,
  content varchar(100) not null,
  registeredDate datetime not null,
  rating int not null
) DEFAULT CHARSET=utf8mb4;
;

drop table reviewBoard;

create table reviewComment
(
  commentIndex int(10) auto_increment
    primary key,
  reviewIndex int(10) not null,
  comment varchar(100) null,
  clientID varchar(50) null,
  registeredDate datetime null
) DEFAULT CHARSET=utf8mb4;
;

drop table reviewComment;

create table reviewImages
(
  reviewIndex int not null,
  imageName varchar(100) not null
    primary key
) DEFAULT CHARSET=utf8mb4;
;

drop table reviewImages;

create table reviewLike
(
  reviewIndex int not null,
  clientID varchar(50) not null,
  checked tinyint(1) default '0' not null,
  clickDate datetime not null
) DEFAULT CHARSET=utf8mb4;
;

create table sCounselImages
(
  cCounselImgIndex int not null,
  imageName varchar(100) null
) DEFAULT CHARSET=utf8mb4;
;

create table ticketing
(
  branchID varchar(20) null,
  clientID varchar(20) null,
  ticketingTime datetime not null,
  waitingNum int not null
) DEFAULT CHARSET=utf8mb4;
;

create table userInfo
(
  id varchar(30) not null
    primary key,
  password varchar(30),
  name varchar(30),
  nickname varchar(30),
  email varchar(30),
  phoneNum varchar(30),
  pwQuestion varchar(100),
  birthDate date,
  registNum varchar(30) null
) DEFAULT CHARSET=utf8mb4;
;